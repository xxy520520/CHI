package com.example;

import com.example.InterceptorConfiguration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception{
        HttpSession session=httpServletRequest.getSession();
        if(null!=session && null!=session.getAttribute(InterceptorConfiguration.SESSION_KEY)){
            return true;
        }
        httpServletResponse.sendRedirect("/login");
        return false;
    }
}