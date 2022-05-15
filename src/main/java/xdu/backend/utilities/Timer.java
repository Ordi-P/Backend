package xdu.backend.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 定时器工具类，用于执行各种定时任务
 *
 * @author 邓乐丰
 */
@Component
public class Timer {

    /**
     * 用于定时删除已毕业的学生记录，根据User表中的grade字段判断
     * TODO: coding 使用DelayQueue来执行定时任务
     */
    public static final void deleteGraduateUser() {

    }

    /**
     * 当用户借书离到期时间差两天（48h）时，发邮件提醒用户还书
     * TODO: coding 同样建议使用DelayQueue执行定时任务
     *
     * @param userID
     */
    public static final void promptReturn(String userID) {

    }

    /**
     * 每周提醒一次用户有未缴罚款
     * TODO: coding
     *
     * @param userID
     */
    public static final void promptFine(String userID) {

    }

}
