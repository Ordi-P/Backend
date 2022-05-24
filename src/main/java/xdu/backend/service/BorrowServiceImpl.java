package xdu.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BookMetaDao;
import xdu.backend.Dao.BorrowDao;
import xdu.backend.Dao.UserDao;
import xdu.backend.exception.*;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;
import xdu.backend.pojo.Borrow;
import xdu.backend.vo.UserBorrowInfo;

import java.sql.Timestamp;
import java.util.*;
import java.sql.Date;

/**
 * @author xduTD
 */
@Service
public class BorrowServiceImpl implements BorrowService {
    /** 匹配ISBN的正则表达式*/
    private static final String ISBNCodeRegex = "^\\d*-\\d*-\\d*-\\d*-\\d$";
    private static final String ISBNNumberRegex = "^\\d{13}$";
    /** 允许借书的时长：30天 */
    private static final long BORROW_DURATION = 10L * 24 * 60 * 60 * 1000;
    /** 续借时间：10天 */
    private static final long RENEW_TIME = 10L * 24 * 60 * 60 * 1000;
    /** 今天23:59:59 */
    private static final long END_TIME_OF_THE_DATE = 24L * 60 * 60 * 1000 - 1;
    /** 用户可以同时借的书本数量 */
    public static final int PERMITTED_BORROW_NUMBER = 5;
    /** 用户预订书籍的过期时间:4h, Timestamp的单位：s */
    public static final long MAX_RESERVE_TIME = 4L * 60 * 60 * 1000;

    @Autowired
    BookDao bookDao;
    @Autowired
    BookMetaDao bookMetaDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BorrowDao borrowDao;


    @Override
    public List<BookMeta> searchBook(String bookInfo) {
        // 如果输入的是空值，返回全部书籍
        if (bookInfo == null || bookInfo.isEmpty()) {
            return bookMetaDao.getAllBookMetas();
        }

        // 添加书本元信息，使用HashMap去重，key为isbnCode，value为bookMeta
        HashMap<String, BookMeta> distinctMap = new HashMap<>(16);

        if (bookInfo.matches(ISBNNumberRegex)) {
            // 如果输入格式是ISBN码，返回只有一本书的列表，如果未通过这个ISBN
            // 查询到书籍就返回空列表
            return bookMetaDao.queryBookMetaByISBN(bookInfo);
        } else {
            // 根据书名查询
            List<BookMeta> bookMetaList = bookMetaDao.queryBookMetaByName(bookInfo);
            for (BookMeta bookMeta : bookMetaList) {
                if (bookMeta != null) {
                    distinctMap.put(bookMeta.getIsbnCode(), bookMeta);
                }
            }
            // 根据作者查询
            bookMetaList = bookMetaDao.queryBookMetaByAuthor(bookInfo);
            for (BookMeta bookMeta : bookMetaList) {
                if (bookMeta != null) {
                    distinctMap.put(bookMeta.getIsbnCode(), bookMeta);
                }
            }
            // 根据ISBN号查询
            bookMetaList = bookMetaDao.queryBookMetaByISBNNumber(bookInfo);
            for (BookMeta bookMeta : bookMetaList) {
                if (bookMeta != null) {
                    distinctMap.put(bookMeta.getIsbnCode(), bookMeta);
                }
            }
        }

        return new ArrayList<>(distinctMap.values());
    }

    @Override
    public void reserveBook(long bookID, String userID) throws ReserveConflictException,
                                                               UserOperationException,
                                                               BookNotExistsException,
                                                               UserNotExistsException {

        // 首先判断用户和书籍是否存在
        if (userDao.getUserById(userID) == null) {
            throw new UserNotExistsException(userID);
        }
        if (bookDao.queryBookByID(bookID) == null) {
            throw new BookNotExistsException(bookID);
        }

        // 首先判断用户状态
        if (!userDao.getUserEnable(userID)) {
            // 用户不可用（罚款未交）
            throw new UserOperationException("Your still have unpaid fines. You cannot reserve any book before you pay for them.");
        } else if (borrowDao.getUserBorrowNumber(userID) >= PERMITTED_BORROW_NUMBER) {
            throw new UserOperationException("You've borrow " + PERMITTED_BORROW_NUMBER +" books. You cannot reserve any book before you return books");
        }

        // 再判断书籍状态
        if (!bookDao.queryBookAvailability(bookID)) {
            // 如果书已被借出
            throw new ReserveConflictException("The book has been lent out.");
        } else {
            // 未被借出，如果书已被预订，判断上一次预订是否过期
            Timestamp reservedTime = bookDao.queryReservedTime(bookID);
            Timestamp expiredTime = new Timestamp(System.currentTimeMillis() - MAX_RESERVE_TIME);

            if (reservedTime == null || reservedTime.before(expiredTime)) {
                // 过期了，可以预订
                bookDao.updateBookReservation(bookID, userID, new Timestamp(System.currentTimeMillis()));
            } else {
                // 没过期，抛出异常
                throw new ReserveConflictException("The book has been reserved.");
            }
        }
    }

    @Override
    public List<UserBorrowInfo> queryMyBorrow(String userID) throws UserNotExistsException {
        // 如果用户不存在，抛出异常信息
        if (userDao.getUserById(userID) == null) {
            throw new UserNotExistsException(userID);
        }

        return borrowDao.queryUserCurrentBorrowInfoByUserID(userID);
    }

    @Override
    public void lendOutBook(long bookID, String userID) throws LendOutConflictException,
                                                               UserOperationException,
                                                               UserNotExistsException,
                                                               BookNotExistsException {
        // 首先判断用户和书籍是否存在
        if (userDao.getUserById(userID) == null) {
            throw new UserNotExistsException(userID);
        }
        if (bookDao.queryBookByID(bookID) == null) {
            throw new BookNotExistsException(bookID);
        }

        // 再判断用户状态
        if (!userDao.getUserEnable(userID)) {
            // 用户不可用（罚款未交）
            throw new UserOperationException("Your still have unpaid fines. You cannot borrow any book before you pay for them.");
        } else if (borrowDao.getUserBorrowNumber(userID) >= PERMITTED_BORROW_NUMBER) {
            // 用户借书已达上限
            throw new UserOperationException("You've borrow " + PERMITTED_BORROW_NUMBER +" books. You cannot borrow any more before you return some of them");
        } else {
            // 判断用户每条借书记录是否都未逾期
            List<UserBorrowInfo> borrowList = borrowDao.queryUserCurrentBorrowInfoByUserID(userID);
            for (UserBorrowInfo borrowInfo : borrowList) {
                // 将借书时间延后为当天的最后一刻，当判断借书时间和当前时间的先后关系时可以直接使用before比较
                Date borrowDate = borrowInfo.getBorrowDate();
                borrowDate.setTime(borrowDate.getTime() + END_TIME_OF_THE_DATE);

                if (borrowDate.before(new Date(System.currentTimeMillis() - MAX_RESERVE_TIME))) {
                    // 逾期,设置enable为false,并抛出异常
                    userDao.updateUserEnable(false, userID);
                    throw new UserOperationException("Your still have unpaid fines. You cannot borrow any book before you pay for them.");
                }
            }
        }

        // 再判断书籍状态
        if (!bookDao.queryBookAvailability(bookID)) {
            // 如果书已被借出
            throw new LendOutConflictException("The book has been lent out.");
        } else {
            // 如果书已被预订，首先判断预订是否过期
            Timestamp reservedTime = bookDao.queryReservedTime(bookID);
            Date today = new java.sql.Date(System.currentTimeMillis());
            Timestamp expiredTime = new Timestamp(today.getTime() - MAX_RESERVE_TIME);

            if (reservedTime == null || reservedTime.before(expiredTime)) {
                // 预订已经过期，可以直接借书，借书记录插入当前时间
                bookDao.updateBookAvailability(bookID, false);
                borrowDao.insertBorrowRecord(bookID, userID, new Date(System.currentTimeMillis()));
            } else {
                // 没过期，再判断预订用户id是不是当前借书用户id
                if (bookDao.queryReserveUserID(bookID).equals(userID)) {
                    // 预订用户id和借书用户id相同，可以借书
                    bookDao.updateBookAvailability(bookID, false);
                    borrowDao.insertBorrowRecord(bookID, userID, new Date(System.currentTimeMillis()));
                    String isbnNumber = bookDao.queryISBNNumberByID(bookID);
                    bookDao.undoBookReservation(userID, isbnNumber, new Timestamp(System.currentTimeMillis() - MAX_RESERVE_TIME - 1));
                } else {
                    throw new LendOutConflictException("The book has been reserved.");
                }
            }
        }

    }

    @Override
    public List<Book> getBook(String isbnNumber) {
        return bookDao.queryBookByISBN(isbnNumber);
    }

    @Override
    public void renew(long bookID, String userID) throws UserNotExistsException,
                                                         BookNotExistsException,
                                                         UserOperationException,
                                                         BorrowTimeExpireException {
        // 首先判断用户和书籍是否存在
        if (userDao.getUserById(userID) == null) {
            throw new UserNotExistsException(userID);
        }
        if (bookDao.queryBookByID(bookID) == null) {
            throw new BookNotExistsException(bookID);
        }
        // 再判断用户状态
        if (!userDao.getUserEnable(userID)) {
            // 用户不可用（罚款未交）
            throw new UserOperationException("Your still have unpaid fines. You cannot borrow any book before you pay for them.");
        }
        // 再判断用户是否为借书人
        if (!userID.equals(borrowDao.queryBorrowerByBookID(bookID))) {
            throw new UserOperationException("The user with ID:" + userID + " is not the borrower of book " + bookID);
        }

        // 都正常，再判断借书时间是否逾期
        Date borrowDate = borrowDao.queryBorrowDateByBookID(bookID);
        borrowDate.setTime(borrowDate.getTime() + END_TIME_OF_THE_DATE);
        if (borrowDate.before(new Date(System.currentTimeMillis() - BORROW_DURATION))) {
            // 如果书已逾期，不允许续借，并且设置用户enable为false
            userDao.updateUserEnable(false, userID);
            throw new BorrowTimeExpireException(bookDao.queryBookNameByID(bookID));
        } else {
            // 书未逾期，将借书时间延后10天，达到续借的目的
            borrowDao.updateBorrowRecord(bookID, new Date(borrowDate.getTime() + RENEW_TIME));
        }
    }

}
