package com.example.service;

import com.example.dao.UserDao;
import com.example.domain.Response;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private UserDao userDao;

    public User login(String username, String password){
        try {
            User user = userDao.getAccountByName(username);
            if (null == user || !user.getPassword().equals(password)) {
                return null;
            }
            return user;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response register(String username, String password){
        try {
            User user = userDao.getAccountByName(username);
            if (user != null) {
                return Response.buildFailure(ACCOUNT_EXIST);
            } else {
                userDao.createNewAccount(username, password);
                return Response.buildSuccess();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
