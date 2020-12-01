package com.example.dao;

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
public class UserDaoTest{
    @Autowired
    private UserDao userDao;

    private User user;

    /*@Test
    public void createNewAccount() {
        int count = userDao.createNewAccount("test", "123456");
        assertTrue(count > 0);
    }*/

    @Test
    public void getAccountByName1() {
        User user = userDao.getAccountByName("admin");
        assertEquals("123456",user.getPassword());
    }

    @Test
    public void getAccountByName2() {
        User user = userDao.getAccountByName("ad");
        assertEquals(null,user);
    }
}
