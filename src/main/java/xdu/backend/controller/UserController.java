package xdu.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import xdu.backend.pojo.Admin;
import xdu.backend.pojo.User;
import xdu.backend.service.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    String registerUser(@RequestBody User user){
        if(user == null){
            return "failed";
        }
        int res = userService.registerUser(user);
        if(res > 0){
            return "success";
        }
        return "failed";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    String login(@RequestBody User user, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        if(user == null){
            return "failed";
        }
        boolean flag = userService.login(user,session,request,response);
        if(flag)
            return "success";
        else
            return "failed";
    }

    @RequestMapping("/logout")
    String logout(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies) {
            if ("cookieUserName".equals(cookie.getName())){
                session.removeAttribute("userTicket:" + cookie.getValue());
                return "success";
            }
        }
        return "fail";
    }

    @GetMapping("/changename/{newName}/{userId}")
    @ResponseBody
    String changeName(@PathVariable("newName") String newName, @PathVariable("userId") String userId){
            boolean flag =  userService.changeNameById(newName, userId);
        if(flag)
            return "success";
        else
            return "failed";
    }

    @RequestMapping("/getUserByCookie")
    @ResponseBody
    User getUserByCookie(HttpServletRequest request,HttpSession session){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        for(Cookie item : cookies){
            if("cookieUserName".equals(item.getName())){
                cookie = item;
                break;
            }
        }
        if (cookie == null) return null;
        User user = ((User) session.getAttribute("userTicket:" + cookie.getValue()));
        return user;
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    @ResponseBody
    String adminLogin(@RequestBody Admin admin, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        if(admin == null) return "false";
        boolean flag = userService.adminLogin(admin,session,request,response);
        if(flag){
            return "success";
        }else{
            return "failed";
        }
    }

    @RequestMapping("/admin/logout")
    String adminLogout(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie:cookies) {
//            if (cookie.getName().equals("adminCookie")){
//                session.removeAttribute(cookie.getValue());
//                return "success";
//            }
//        }
        return "sueeess";
//        return "fail";
    }

    @RequestMapping("/updatePassword")
    String updatePassword(@RequestBody String string){
        System.out.println(string);
       if(string == null) return "failed";
        String password = "";
        String id = "";
        id = string.substring(17,28);
        for(int i = 54; i < string.length(); i++){
            if(string.charAt(i) == '%') break;
            password += string.charAt(i);
        }

        boolean flag = userService.updatePasswordById(id,password);
        if (flag) return "success";
        return "failed";
    }

    @RequestMapping("/updateEmail")
    String updateEmail(@RequestBody User user){
        String email = user.getEmail();
        if(email == null) return "false";
        if(userService.updateEmailByUserId(user.getId(),email) > 0){
            return "success";
        }
        return "failed";
    }

}
