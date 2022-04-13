package xdu.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xdu.backend.pojo.User;
import xdu.backend.service.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
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

    @GetMapping("/changename/{newName}/{userId}")
    @ResponseBody
    String changeName(@PathVariable("newName") String newName, @PathVariable("userId") Long userId){
        boolean flag =  userService.changeNameById(newName, userId);
        if(flag)
            return "success";
        else
            return "failed";
    }

    @RequestMapping("/getUserByCookie")
    @ResponseBody
    User getUserBycookie(HttpServletRequest request,HttpSession session){
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        for(Cookie item : cookies){
            if(item.getName().equals("cookieUserName")){
                cookie = item;
                break;
            }
        }
        User user = ((User) session.getAttribute("userTicket:" + cookie.getValue()));
        return user;
    }


}
