package com.example.web;

import com.example.InterceptorConfiguration;
import com.example.domain.Response;
import com.example.domain.User;
import com.example.domain.userForm;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    @Autowired
    private UserService userService;

    @RequestMapping(value="/api/user/login",method=RequestMethod.POST)
    public Response login(@RequestBody userForm userform, HttpSession session){
        User user = userService.login(userform.username,userform.password);
        if(user==null){
            return Response.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userform);
        return Response.buildSuccess(user);
    }

    @RequestMapping(value="/api/user/register",method=RequestMethod.POST)
    public Response register(@RequestBody userForm userform){
        return userService.register(userform.username,userform.password);
    }

}
