package xdu.backend.service;

import xdu.backend.exception.*;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;
import xdu.backend.vo.UserBorrowInfo;

import java.util.List;

public interface BorrowService {
    /**
     * 查找书籍
     *
     * @param bookInfo
     * @return 书籍元信息的列表
     */
    List<BookMeta> searchBook(String bookInfo);

    /**
     * 预订书籍
     *
     * @param bookID
     * @param userID
     * @throws ReserveConflictException
     */
    void reserveBook(long bookID, String userID) throws ReserveConflictException,
            UserOperationException, BookNotExistsException, UserNotExistsException;

    /**
     * 查询我已借阅的书籍
     *
     * @param userID
     * @return
     */
    List<UserBorrowInfo> queryMyBorrow(String userID) throws UserNotExistsException;

    /**
     * 借书出库
     *
     * @param bookID
     * @param userID
     */
    void lendOutBook(long bookID, String userID) throws LendOutConflictException,
            UserOperationException, UserNotExistsException, BookNotExistsException;

    /**
     * 返回某一个isbn下的所有书本
     *
     * @param isbnNumber
     * @return
     */
    List<Book> getBook(String isbnNumber);

    /**
     * 续借书籍（10天）
     *
     * @param bookID
     * @param userID
     */
    void renew(long bookID, String userID) throws UserNotExistsException, BookNotExistsException, UserOperationException, BorrowTimeExpireException;
}
