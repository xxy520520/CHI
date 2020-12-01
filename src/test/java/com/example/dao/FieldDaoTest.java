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
public class FieldDaoTest {
    @Autowired
    FieldDao fieldDao;

    @Test
    public void orderByAmount(){
        List<FieldOrderInfo> list = fieldDao.orderByAmount(1,10);
        assertNotNull(list.get(0).getFieldName());
        assertTrue(list.get(0).getPaperNumber()>0);
    }

    @Test
    public void orderByActivation(){
        List<FieldOrderInfo> list = fieldDao.orderByActivation(1,10);
        assertNotNull(list.get(0).getFieldName());
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getActivation()>=50);
    }

    @Test
    public void orderByCitation(){
        List<FieldOrderInfo> list = fieldDao.orderByCitation(1,10);
        assertNotNull(list.get(0).getFieldName());
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getCitation()>=100);
    }

    @Test
    public void number(){
        int count = fieldDao.numberOfFields();
        assertTrue(count>100);
    }

    @Test
    public void getTotalPapers(){
        int number =fieldDao.getTotalPapers(1);
        assertTrue(number>0);
    }

    @Test
    public void getTotalCitations(){
        int number = fieldDao.getTotalCitations(1);
        assertTrue(number>0);
    }

    @Test
    public void getByIdSuccess(){
        Field field = fieldDao.getById(1);
        assertNotNull(field.getFieldName());
    }

    @Test
    public void getByIdFailure(){
        Field field = fieldDao.getById(10000);
        assertEquals(null,field);
    }

    @Test
    public void getAffiliations(){
        List<AffiliationOrderInfo> list = fieldDao.getAffiliations(41);
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getAffiliationId()>0);
        assertTrue(list.get(0).getCitation()>0);
        assertNotNull(list.get(0).getAffiliationName());
    }

    @Test
    public void getAuthors(){
        List<AuthorOrderInfo> list = fieldDao.getAuthors(41);
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getAuthorId()>0);
        assertTrue(list.get(0).getCitation()>0);
        assertNotNull(list.get(0).getAuthorName());
    }

    @Test
    public void getPapers(){
        List<PaperOrderInfo> list = fieldDao.getPapers(41);
        assertTrue(list.get(0).getPaperId()>0);
        assertTrue(list.get(0).getReference()>0);
        assertNotNull(list.get(0).getPaperName());
    }

    @Test
    public void getMeetings(){
        List<MeetingOrderInfo> list = fieldDao.getMeetings(41);
        assertTrue(list.get(0).getCitation()>0);
        assertTrue(list.get(0).getPaperNumber()>0);
        assertNotNull(list.get(0).getMeetingName());
    }
}