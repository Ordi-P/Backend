package xdu.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xdu.backend.Dao.UserDao;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,user_id,book_id,book_name,isbn_number,isbn_code,book_author,num");
        response.setHeader("Set-Cookie", "token=cowshield");

        if (null == cookies) {
            response.getWriter().write("failed");
            return false;
        }
        String cookieValue = null;
        for (Cookie item : cookies) {
            if ("cookieUserName".equals(item.getName())) {
                cookieValue = item.getValue();
                break;
            }
        }
        if (StringUtils.isEmpty(cookieValue)) {
            response.getWriter().write("failed");
            return false;
        }
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("userTicket:" + cookieValue);
        if (null == obj) {
            response.getWriter().write("failed");
        }
        // 已经登录
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

