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

import java.sql.Timestamp;
import java.util.*;
import java.sql.Date;

@Service
public class BorrowServiceImpl implements BorrowService {
    /** 匹配ISBN的正则表达式*/
    private static final String ISBNCodeRegex = "^\\d*-\\d*-\\d*-\\d*-\\d$";
    private static final String ISBNNumberRegex = "^\\d{13}$";
    /** 用户可以同时借的书本数量 */
    public static final int PERMITTED_BORROW_NUMBER = 5;
    /** 用户预订书籍的过期时间:4h, Timestamp的单位：s */
    public static final int MAX_RESERVE_TIME = 4 * 60 * 60 * 1000;

    @Autowired
    BookDao bookDao;
    @Autowired
    BookMetaDao bookMetaDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BorrowDao borrowDao;

    /**
     * 查找书籍
     *
     * @param bookInfo 书籍信息
     * @return bookList
     */
    @Override
    public List<BookMeta> searchBook(String bookInfo) {
        // 如果输入的是空值，返回全部书籍
        if (bookInfo == null || bookInfo.isEmpty()) {
            return bookMetaDao.getAllBookMetas();
        }

        // 添加书本元信息，使用HashMap去重，key为isbnCode，value为bookMeta
        HashMap<String, BookMeta> distinctMap = new HashMap<>();
        /* version1:
        // 添加书本，使用HashMap去重，key为bookID，value为Book
        HashMap<String, Book> distinctMap = new HashMap<>();
        */

        if (bookInfo.matches(ISBNNumberRegex)) {
            // 如果输入格式是ISBN码，返回只有一本书的列表，如果未通过这个ISBN
            // 查询到书籍就返回空列表
            return bookMetaDao.queryBookMetaByISBN(bookInfo);
            /* version1的实现：返回List<Book>, 再去重
            List<Book> bookList = bookDao.queryBookByISBNCode(bookInfo);
            for (Book book : bookList) {
                if (book != null) {
                    distinctMap.put(book.getBookID().toString(), book);
                }
            }
             */
        } else {
            // 根据书名查询
            List<BookMeta> bookMetaList = bookMetaDao.queryBookMetaByName(bookInfo);
            for (BookMeta bookMeta : bookMetaList) {
                if (bookMeta != null) {
                    distinctMap.put(bookMeta.getIsbnCode(), bookMeta);
                }
            }
            /* version1:
            List<Book> bookList = bookDao.queryBookByName(bookInfo);
            for (Book book : bookList) {
                if (book != null) {
                    distinctMap.put(book.getBookID().toString(), book);
                }
            }
             */
            // 根据作者查询
            bookMetaList = bookMetaDao.queryBookMetaByAuthor(bookInfo);
            for (BookMeta bookMeta : bookMetaList) {
                if (bookMeta != null) {
                    distinctMap.put(bookMeta.getIsbnCode(), bookMeta);
                }
            }
            /*
            bookList = bookDao.queryBookByAuthor(bookInfo);
            for (Book book : bookList) {
                if (book != null) {
                    distinctMap.put(book.getBookID().toString(), book);
                }
            }
             */
            // 根据ISBN号查询
            bookMetaList = bookMetaDao.queryBookMetaByISBNNumber(bookInfo);
            for (BookMeta bookMeta : bookMetaList) {
                if (bookMeta != null) {
                    distinctMap.put(bookMeta.getIsbnCode(), bookMeta);
                }
            }
            /* version1:
            bookList = bookDao.queryBookByISBNNumber(bookInfo);
            for (Book book : bookList) {
                if (book != null) {
                    distinctMap.put(book.getBookID().toString(), book);
                }
            }
             */
        }

        return new ArrayList<>(distinctMap.values());
    }

    /**
     * 预订书籍
     *
     * @param bookID
     * @param userID
     */
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
            Timestamp expiredTime = new Timestamp(new java.util.Date().getTime() - MAX_RESERVE_TIME);

            if (reservedTime == null || reservedTime.before(expiredTime)) {
                // 过期了，可以预订
                bookDao.updateBookReservation(bookID, userID, new Timestamp(new java.util.Date().getTime()));
            } else {
                // 没过期，抛出异常
                throw new ReserveConflictException("The book has been reserved.");
            }
        }

    }

    /**
     * 查询我已借阅的书籍
     *
     * @param userID
     * @return bookList
     */
    @Override
    public List<Book> queryMyBorrow(String userID) throws UserNotExistsException {
        // 如果用户不存在，抛出异常信息
        if (userDao.getUserById(userID) == null) {
            throw new UserNotExistsException(userID);
        }

        return borrowDao.getUserBorrowList(userID);
    }

    /**
     * 借书出库
     *
     * @param bookID
     * @param userID
     */
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
            throw new UserOperationException("You've borrow " + PERMITTED_BORROW_NUMBER +" books. You cannot borrow any more before you return some of them");
        }

        // 再判断书籍状态
        if (!bookDao.queryBookAvailability(bookID)) {
            // 如果书已被借出
            throw new LendOutConflictException("The book has been lent out.");
        } else {
            // 如果书已被预订，首先判断预订是否过期
            Timestamp reservedTime = bookDao.queryReservedTime(bookID);
            Timestamp expiredTime = new Timestamp(new java.util.Date().getTime() - MAX_RESERVE_TIME);

            if (reservedTime == null || reservedTime.before(expiredTime)) {
                // 预订已经过期，可以直接借书，借书记录插入当前时间
                bookDao.updateBookAvailability(bookID, false);
                borrowDao.insertBorrowRecord(bookID, userID, new Date(new java.util.Date().getTime()));
            } else {
                // 没过期，再判断预订用户id是不是当前借书用户id
                if (bookDao.queryReserveUserID(bookID).equals(userID)) {
                    // 预订用户id和借书用户id相同，可以借书
                    bookDao.updateBookAvailability(bookID, false);
                    borrowDao.insertBorrowRecord(bookID, userID, new Date(new java.util.Date().getTime()));
                    String isbnNumber = bookDao.queryISBNNumberByID(bookID);
                    bookDao.undoBookReservation(userID, isbnNumber, new Timestamp(new java.util.Date().getTime() - MAX_RESERVE_TIME - 1));
                } else {
                    throw new LendOutConflictException("The book has been reserved.");
                }
            }

        }

    }

    /**
     * 返回某一个isbn下的所有书本
     * @param isbnNumber
     * @return
     */
    public List<Book> getBook(String isbnNumber) {
        return bookDao.queryBookByISBN(isbnNumber);
    }
}
