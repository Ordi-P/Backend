package xdu.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BorrowDao;
import xdu.backend.Dao.UserDao;
import xdu.backend.exception.LendOutConflictException;
import xdu.backend.exception.ReserveConflictException;
import xdu.backend.exception.UserOperationException;
import xdu.backend.pojo.Book;

import java.sql.Timestamp;
import java.util.*;
import java.sql.Date;

@Service
public class BorrowServiceImpl implements BorrowService {
    /** 匹配ISBN的正则表达式*/
    private static final String ISBNCodeRegex = "";
    /** 用户可以同时借的书本数量 */
    public static final int PERMITTED_BORROW_NUMBER = 5;
    /** 用户预订书籍的过期时间:4h, Timestamp的单位：s */
    public static final int MAX_RESERVE_TIME = 4 * 60 * 60;

    @Autowired
    BookDao bookDao;
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
    public List<Book> searchBook(String bookInfo) {
        // 如果输入的是空值，返回全部书籍
        if (bookInfo == null || bookInfo.isEmpty()) {
            return bookDao.getAllBooks();
        }

        // 添加书本，使用HashMap去重，key为bookID，value为Book
        HashMap<String, Book> distinctMap = new HashMap<>();

        if (bookInfo.matches(ISBNCodeRegex)) {
            // 如果输入格式是ISBN码，返回只有一本书的列表，如果
            // 不匹配就返回空列表
            Book book = bookDao.queryBookByISBNCode(bookInfo);
            if (book != null) {
                distinctMap.put(book.getBookID(), book);
            }
            return new ArrayList<>(distinctMap.values());
        } else {
            // 根据书名查询
            List<Book> bookList = bookDao.queryBookByName(bookInfo);
            for (Book book : bookList) {
                if (book != null) {
                    distinctMap.put(book.getBookID(), book);
                }
            }
            // 根据作者查询
            bookList = bookDao.queryBookByAuthor(bookInfo);
            for (Book book : bookList) {
                if (book != null) {
                    distinctMap.put(book.getBookID(), book);
                }
            }
            // 根据ISBN号查询
            bookList = bookDao.queryBookByISBNNumber(bookInfo);
            for (Book book : bookList) {
                if (book != null) {
                    distinctMap.put(book.getBookID(), book);
                }
            }
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
    public void reserveBook(String bookID, String userID) throws ReserveConflictException,
                                                                 UserOperationException {

        // 首先判断用户状态
        if (userDao.getUserEnable(userID)) {
            // 用户不可用（罚款未交）
            throw new UserOperationException("Your still have unpaid fines. You cannot reserve any book before you pay for them.");
        } else if (borrowDao.getUserBorrowNumber(userID) >= PERMITTED_BORROW_NUMBER) {
            throw new UserOperationException("You've borrow " + PERMITTED_BORROW_NUMBER +" books. You cannot reserve any book before you return books");
        }

        // 再判断书籍状态
        if (!bookDao.getBookAvailability(bookID)) {
            // 如果书已被借出
            throw new ReserveConflictException("The book has been lent out.");
        } else {
            // 未被借出，如果书已被预订，判断上一次预订是否过期
            Timestamp reservedTime = bookDao.getReservedTime(bookID);
            Timestamp expiredTime = new Timestamp(new java.util.Date().getTime() - MAX_RESERVE_TIME);
            if (reservedTime.after(expiredTime)) {
                // 没过期，抛出异常
                throw new ReserveConflictException("The book has been reserved.");
            } else {
                // 过期了，可以预订
                bookDao.setBookReservation(bookID, userID, new Timestamp(new java.util.Date().getTime()));
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
    public List<Book> queryMyBorrow(String userID) {
        return null;
    }

    /**
     * 借书出库
     *
     * @param bookID
     * @param userID
     */
    @Override
    public void lendOutBook(String bookID, String userID) throws LendOutConflictException,
                                                                 UserOperationException {
        /*
        // 首先判断用户状态
        if (userDao.getUserEnable(id)) {
            // 用户不可用（罚款未交）
            throw new UserOperationException("Your still have unpaid fines. You cannot borrow any book before you pay for them.");
        } else if (borrowDao.getUserBorrowNumber(id) >= PERMITTED_BORROW_NUMBER) {
            throw new UserOperationException("You've borrow " + PERMITTED_BORROW_NUMBER +" books. You cannot borrow any more before you return some of them");
        }

        // 再判断书籍状态
        if (!bookDao.getBookAvailability(bookID)) {
            // 如果书已被借出
            throw new LendOutConflictException("The book has been lent out.");
        } else {
            // 如果书已被预订，首先判断预订是否过期
            Timestamp reservedTime = bookDao.getReservedTime(bookID);
            Timestamp expiredTime = new Timestamp(new java.util.Date().getTime() - 4 * 60 * 60);
            if (reservedTime.after(expiredTime)) {
                // 没过期，再判断预订用户id是不是当前借书用户id
                if (bookDao.getReserveUserID(bookID).equals(id)) {
                    // 预订用户id和借书用户id相同，可以借书
                    bookDao.updateBookAvailability(bookID, false);
                    borrowDao.insertBorrowRecord(bookID, id, new Date(new java.util.Date().getTime()));
                } else {
                    throw new LendOutConflictException("The book has been reserved.");
                }
            } else {
                // 预订已经过期，可以直接借书
                bookDao.updateBookAvailability(bookID, false);
                borrowDao.insertBorrowRecord(bookID, id, new Date(new java.util.Date().getTime()));
            }
        }

         */
    }


}
