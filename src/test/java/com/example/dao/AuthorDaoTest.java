package com.example.dao;

import com.example.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorDaoTest {
    @Autowired
    AuthorDao authorDao;

    @Test
    public void orderByAmount(){
        List<AuthorOrderInfo> list = authorDao.orderByAmount(1,10);
        assertNotNull(list.get(0).getAuthorName());
        assertTrue(list.get(0).getPaperNumber()>0);
    }

    @Test
    public void orderByActivation(){
        List<AuthorOrderInfo> list = authorDao.orderByActivation(1,10);
        assertNotNull(list.get(0).getAuthorName());
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getActivation()>=19);
    }

    @Test
    public void orderByCitation(){
        List<AuthorOrderInfo> list = authorDao.orderByCitation(1,10);
        assertNotNull(list.get(0).getAuthorName());
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getCitation()>=50);
    }

    @Test
    public void getCooperators(){
        List<Author> list = authorDao.getCooperators(646);
        assertNotNull(list.get(0).getAuthorName());
        assertTrue(list.get(0).getAuthorId()>0);
        assertTrue(list.get(0).getActivation()>=0);
    }

    @Test
    public void ifCooperator(){
        int number = authorDao.ifCooperator(656,654);
        assertTrue(number>=0&&number<=1);
    }

    @Test
    public void getTotalPapers(){
        int number =authorDao.getTotalPapers(1);
        assertTrue(number>0);
    }

    @Test
    public void getTotalCitations(){
        int number = authorDao.getTotalCitations(3);
        assertTrue(number>0);
    }

    @Test
    public void getNowAffiliation(){
        Affiliation affiliation = authorDao.getNowAffiliation(1);
        assertNotNull(affiliation.getAffiliationName());
        assertTrue(affiliation.getAffiliationId()>0);
    }

    @Test
    public void getRepresentPaper(){
        PaperOrderInfo info = authorDao.getRepresentPaper(3);
        assertNotNull(info.getPaperName());
        assertTrue(info.getPaperId()>0);
    }

    @Test
    public void getRepresentFields(){
        List<Field> list = authorDao.getRepresentFields(652);
        assertNotNull(list.get(0).getFieldName());
        assertTrue(list.get(0).getFieldId()>0);
    }

    @Test
    public void getNowFields(){
        List<Field> list = authorDao.getNowFields(652);
        assertNotNull(list.get(0).getFieldName());
        assertTrue(list.get(0).getFieldId()>0);
    }


    @Test
    public void number(){
        int count = authorDao.numberOfAuthors();
        assertTrue(count>0);
    }

    @Test
    public void getByIdSuccess(){
        Author author = authorDao.getById(1);
        assertNotNull(author.getAuthorName());
    }

    @Test
    public void getByIdFailure(){
        Author author = authorDao.getById(10000);
        assertEquals(null,author);
    }

    @Test
    public void getMainMeeting(){
        String name = authorDao.getMainMeeting(3);
        assertNotNull(name);
    }

    @Test
    public void orderByGIndex() {
        List<AuthorOrderInfo> list = authorDao.orderByGIndex(1,10);
        assertNotNull(list.get(0).getAuthorName());
        assertTrue(list.get(0).getPaperNumber()>0);
        for(int i=0;i<10;i++){
            System.out.println(list.get(i).getgIndex());
        }
    }

}