package xdu.backend.service;

import com.alibaba.fastjson.JSONObject;
import xdu.backend.exception.*;
import xdu.backend.pojo.Book;
import xdu.backend.pojo.BookMeta;
import xdu.backend.vo.AbandonedBook;
import xdu.backend.vo.BookInfo;
import xdu.backend.vo.UserBorrowInfo;

import java.util.List;

/**
 * @author 邓乐丰
 */
public interface BorrowService {
    /**
     * 查找书籍如果输入符合ISBN的格式，则只根据ISBN进
     * 行查询；如果不符合ISBN的格式，则将会从作者、书
     * 名、ISBN三个方向匹配，返回匹配的所有结果
     *
     * @param bookInfo 书籍的相关信息。
     * @return 书籍元信息的列表
     */
    List<BookInfo> searchBook(String bookInfo);

    /**
     * 预订书籍
     *
     * @param bookID 书籍ID
     * @param userID 用户ID（学号）
     * @throws ReserveConflictException
     */
    void reserveBook(long bookID, String userID) throws ReserveConflictException,
            UserOperationException, BookNotExistsException, UserNotExistsException;

    /**
     * 查询当前我已借阅的书籍（未还）
     *
     * @param userID 用户ID （学号）
     * @return 书籍本次借阅信息的列表
     */

    List<UserBorrowInfo> queryMyCurrentBorrow(String userID) throws UserNotExistsException;

    /**
     * 查询我的历史借阅记录（已还）
     *
     * @param userID 用户ID （学号）
     * @return 书籍本次借阅信息的列表
     */

    List<UserBorrowInfo> queryMyHistoryBorrow(String userID) throws UserNotExistsException;

    /**
     * 借书出库
     *
     * @param bookID 书籍ID
     * @param userID 用户ID
     */
    void lendOutBook(long bookID, String userID) throws LendOutConflictException,
            UserOperationException, UserNotExistsException, BookNotExistsException;

    /**
     * 返回某一个ISBN下的所有书籍副本
     *
     * @param isbnNumber ISBN数
     * @return 书籍列表
     */
    List<Book> getBook(String isbnNumber);

    /**
     * 续借书籍（10天）
     *
     * @param transactionID 借书记录事务ID
     * @param bookID 书籍ID
     * @param userID 用户ID
     */
    void renew(Long transactionID, Long bookID, String userID) throws UserNotExistsException, BookNotExistsException, UserOperationException, BorrowTimeExpireException;

    /**
     * 每次管理员加载主界面的时候都会执行一次这个请求
     *
     * @return int[] 四个数字：
     *        [0] collectionNumber 馆藏书籍数量
     *        [1] lentoutNumber 借出的书籍数量
     *        [2] damagedNumber 损坏的书籍数量
     *        [3] lostNumber 丢失的书籍数量
     */
    int[] queryBookStates();

    /**
     * 查询因丢失而删除的书籍的详细信息
     *
     * @return lostBookList 丢失而删除的书的列表
     */
    List<AbandonedBook> queryLostBooks();

    /**
     * 查询因损坏而删除的书籍的详细信息
     *
     * @return damagedBookList 损坏而删除的书的列表
     */
    List<AbandonedBook> queryDamagedBooks();
}
