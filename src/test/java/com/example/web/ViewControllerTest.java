package com.example.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewControllerTest {
    @Autowired
    ViewController viewController;

    @Test
    public void getLogin() {
        String re=viewController.getLogin();
        assertEquals("login",re);
    }

    @Test
    public void getSignUp() {
        String re=viewController.getSignUp();
        assertEquals("signUp",re);
    }

    @Test
    public void getPaperList() {
        String re=viewController.getPaperList();
        assertEquals("paperList",re);
    }

    @Test
    public void getPaperDetail() {
        String re=viewController.getPaperDetail(1);
        assertEquals("paperDetail",re);
    }

    @Test
    public void getPaperOrder() {
        String re=viewController.getPaperOrder();
        assertEquals("paperOrder",re);
    }

    @Test
    public void getAuthorOrder() {
        String re=viewController.getAuthorOrder();
        assertEquals("authorOrder",re);
    }

    @Test
    public void getAffiliationOrder() {
        String re=viewController.getAffiliationOrder();
        assertEquals("affiliationOrder",re);
    }

    @Test
    public void getSearchPage() {
        String re=viewController.getSearchPage();
        assertEquals("searchPage",re);
    }

    @Test
    public void getAffiliationMap() {
        String re=viewController.getAffiliationMap();
        assertEquals("affiliationMap",re);
    }

    @Test
    public void getAuthorMap() {
        String re=viewController.getAuthorMap();
        assertEquals("authorMap",re);
    }

    @Test
    public void getMeetingMap() {
        String re=viewController.getMeetingMap();
        assertEquals("meetingMap",re);
    }

    @Test
    public void getAuthorNetwork() {
        String re=viewController.getAuthorNetWork();
        assertEquals("authorNetWork",re);
    }

    @Test
    public void getFieldMap() {
        String re=viewController.getFieldMap();
        assertEquals("fieldMap",re);
    }

    @Test
    public void getFieldOrder() {
        String re=viewController.getFieldOrder();
        assertEquals("fieldOrder",re);
    }
}