package com.example.service;

import com.example.domain.Response;
import com.example.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void login1() {
        User user = userService.login("admin", "123456");
        assertTrue(user!=null);
    }

    @Test
    public void login2() {
        User user = userService.login("admin", "1234");
        assertTrue(user==null);
    }

    @Test
    public void login3() {
        User user = userService.login("adm", "123456");
        assertTrue(user==null);
    }

    @Test
    public void register3() {
        Response response = userService.register("admin", "123456");
        assertEquals("账号已存在",response.getMessage());
    }


}
