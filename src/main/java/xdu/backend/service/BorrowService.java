package xdu.backend.service;

import xdu.backend.exception.LendOutConflictException;
import xdu.backend.exception.ReserveConflictException;
import xdu.backend.exception.UserNotExistsException;
import xdu.backend.exception.UserOperationException;
import xdu.backend.pojo.Book;

import java.util.List;

public interface BorrowService {
    /**
     * 查找书籍
     *
     * @param bookInfo
     * @return
     */
    List<Book> searchBook(String bookInfo);

    /**
     * 预订书籍
     *
     * @param bookID
     * @param userID
     * @throws ReserveConflictException
     */
    void reserveBook(String bookID, String userID) throws ReserveConflictException,
            UserOperationException;

    /**
     * 查询我已借阅的书籍
     *
     * @param userID
     * @return
     */
    List<Book> queryMyBorrow(String userID) throws UserNotExistsException;

    /**
     * 借书出库
     *
     * @param bookID
     * @param userID
     */
    void lendOutBook(String bookID, String userID) throws LendOutConflictException,
            UserOperationException, UserNotExistsException;

    /**
     * 返回某一个isbn下的所有书本
     *
     * @param isbnCode
     * @return
     */
    List<Book> getBook(String isbnCode);
}
