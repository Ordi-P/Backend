package xdu.backend.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BorrowDao;
import xdu.backend.Dao.UserDao;

import java.util.Date;

/**
 * 实现邮件提醒功能的工具类
 *
 * @author 邓乐丰
 */
@Component
public class EmailServiceExecutor {
    /** 邮件主题 */
    public static final String REGISTER_SUBJECT = "【Xidian Library】Register successfully";
    public static final String RESERVE_SUBJECT = "【Xidian Library】Reserve successfully";
    public static final String RETURN_SUBJECT = "【Xidian Library】Return reminding";
    public static final String FINE_SUBJECT = "【Xidian Library】Fine reminding";

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BorrowDao borrowDao;
    @Autowired
    private UserDao userDao;

    /**
     * 当用户注册成功时，发邮件到用户邮箱提醒用户
     *
     * @param userID 邮件要发给哪个用户
     */
    public final void sendRegisterEmail(String userID) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置收件人邮箱、主题（标题）
        message.setTo(userDao.queryEmailAddressByID(userID));
        message.setSubject(REGISTER_SUBJECT);
        // 邮件正文
        message.setText("Hello " + userID + ":\n " +
                        "You've successfully registered to Xidian Library!\n" +
                        "Your default password is 【123456】. For security's sake, please login and change your password at your home page");

        send(message);
    }

    /**
     * 当用户预定成功时，发邮件告知书籍预定信息并通知用户尽快取书
     *
     * @param userID 用户ID
     * @param bookID 书籍ID
     */
    public final void sendReservePromptEmail(String userID, Long bookID) {
        String address = userDao.queryEmailAddressByID(userID);
        String bookName = bookDao.queryBookNameByID(bookID);
        String location = bookDao.queryLocationByID(bookID);

        SimpleMailMessage message = new SimpleMailMessage();
        // 设置收件人邮箱、主题（标题）
        message.setTo(address);
        message.setSubject(RESERVE_SUBJECT);
        // 邮件正文
        message.setText("You've successfully reserved " + bookName + " .\n" +
                        "Location of the book is " + location + ". We will reserve the book for you for 4 hours.\n");

        send(message);
    }

    /**
     * 某条借书记录的到期时间还差2天时，发送邮件提醒用户还书
     *
     * @param transactionID 借书记录的事务id，主键
     */
    public final void sendReturnPromptEmail(Long transactionID) {
        String address = borrowDao.queryUserEmailByTxID(transactionID);
        String bookName = borrowDao.queryBookNameByTxID(transactionID);

        SimpleMailMessage message = new SimpleMailMessage();
        // 设置收件人邮箱、主题（标题）
        message.setTo(address);
        message.setSubject(RETURN_SUBJECT);
        // 邮件正文
        message.setText("Just a reminder.\n" +
                        "Your borrow of " + bookName + " has 2 days left. Remember to return the book on time or renew before it expires.\n");

        send(message);
    }

    /**
     * 用户有罚款时，发送邮件提醒用户缴纳罚款
     *
     * @param userID 用户ID
     */
    public final void sendFineEmail(String userID) {
        int fine = borrowDao.queryUnpaidFineByUserID(userID);
        String address = userDao.queryEmailAddressByID(userID);

        SimpleMailMessage message = new SimpleMailMessage();
        // 设置收件人邮箱、主题（标题）
        message.setTo(address);
        message.setSubject(FINE_SUBJECT);
        // 邮件正文
        message.setText("You still have unpaid fine.\n" +
                "Total amount of your fine is " + fine + ". Go to your home page to check and pay for it.\n");

        send(message);
    }

    /**
     * 这个是EmailServer的工具方法，以上各种方法都是此方法的二次封装
     *
     * @param message 邮件内容
     */
    private final void send(SimpleMailMessage message) {
        // 发送人（我）
        message.setFrom("funduncan@qq.com");
        // 日期
        message.setSentDate(new Date());
        // 发送
        javaMailSender.send(message);
    }

}
