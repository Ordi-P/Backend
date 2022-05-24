package xdu.backend.exception;

/**
 * 用户操作异常，可能是操作不合法：（但都是系统允许的分支）
 *     1. 用户有未交罚款，但是却使用了借书/预定功能
 *     2. 用户当前已借5本书，又使用了借书/预定功能
 *     3. 还书时用户ID和该书本登记的借书ID不同（under consideration）
 *
 * @author 邓乐丰
 */
public class UserOperationException extends Exception {

    public UserOperationException(String errMsg) {
        super("UserOperationException: " + errMsg);
    }
}
