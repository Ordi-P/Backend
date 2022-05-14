package xdu.backend.utilities;

import org.springframework.stereotype.Component;

/**
 * 实现邮件提醒功能的工具类
 *
 * @author 邓乐丰
 */
@Component
public class EmailServer {

    /**
     * TODO: coding
     *
     * @param emailAddress
     */
    public static final void sendRegisterEmail(String emailAddress) {

    }

    /**
     * TODO: coding
     *
     * @param emailAddress
     */
    public static final void sendReservePromptEmail(String emailAddress) {

    }

    /**
     * TODO: coding
     *
     * @param emailAddress
     */
    public static final void sendReturnPromptEmail(String emailAddress) {

    }

    /**
     * TODO: coding
     *
     * @param emailAddress
     */
    public static final void sendFineEmail(String emailAddress) {

    }

    /**
     * 这个是EmailServer的工具方法，以上各种方法都是此方法的二次封装
     * TODO: coding
     *
     * @param emailAddress 收件人邮箱
     * @param message 邮件内容
     */
    private static final void send(String emailAddress, String message) {

    }

}
