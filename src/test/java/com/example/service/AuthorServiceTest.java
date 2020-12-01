package com.example.service;

import com.example.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    @Test
    public void getAuthorNetwork(){
        Response re=authorService.getAuthorNetwork(654);
        AuthorNetwork authorNetwork=(AuthorNetwork) re.getContent();
        assertNotNull(authorNetwork.getLinks().get(0));
    }

    @Test
    public void orderByAmount(){
        Response re=authorService.orderByAmount(1,12);
        ArrayList<AuthorOrderInfo> authorInfo=(ArrayList<AuthorOrderInfo>)re.getContent();
        assertTrue(authorInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void orderByActivation(){
        Response re=authorService.orderByActivation(1,12);
        ArrayList<AuthorOrderInfo> authorInfo=(ArrayList<AuthorOrderInfo>)re.getContent();
        assertTrue(authorInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void orderByCitation(){
        Response re=authorService.orderByCitation(1,12);
        ArrayList<AuthorOrderInfo> authorInfo=(ArrayList<AuthorOrderInfo>)re.getContent();
        assertTrue(authorInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void getAuthorMap() {
        Response re = authorService.getAuthorMap(652);
        AuthorMap authorMap = (AuthorMap) re.getContent();
        assertNotNull(authorMap.getAuthor());
        assertNotNull(authorMap.getMainMeeting());
        assertTrue(authorMap.getTotalCitations() > 0);
        assertTrue(authorMap.getTotalPapers() > 0);
        assertNotNull(authorMap.getNowFields().get(0));
        assertNotNull(authorMap.getRepresentFields().get(0));
        assertNotNull(authorMap.getRepresentPaper());
        assertNotNull(authorMap.getNowAffiliation().getAffiliationName());
    }

    @Test
    public void orderByGIndex() {
        Response re=authorService.orderByGIndex(1,12);
        ArrayList<AuthorOrderInfo> authorInfo=(ArrayList<AuthorOrderInfo>)re.getContent();
        assertTrue(authorInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

}