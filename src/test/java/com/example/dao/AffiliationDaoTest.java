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
public class AffiliationDaoTest {
    @Autowired
    AffiliationDao affiliationDao;

    @Test
    public void orderByAmount(){
        List<AffiliationOrderInfo> list = affiliationDao.orderByAmount(1,10);
        assertNotNull(list.get(1).getAffiliationName());
        assertTrue(list.get(1).getPaperNumber()>10);
    }

    @Test
    public void orderByActivation(){
        List<AffiliationOrderInfo> list = affiliationDao.orderByActivation(1,10);
        assertNotNull(list.get(1).getAffiliationName());
        assertTrue(list.get(1).getActivation()>10);
    }

    @Test
    public void orderByCitation(){
        List<AffiliationOrderInfo> list = affiliationDao.orderByCitation(1,10);
        assertNotNull(list.get(1).getAffiliationName());
        assertTrue(list.get(1).getCitation()>100);
    }

    @Test
    public void getCooperators(){
        List<Affiliation> list = affiliationDao.getCooperators(12);
        assertNotNull(list.get(0).getAffiliationName());
        assertTrue(list.get(0).getAffiliationId()>0);
        assertTrue(list.get(0).getActivation()>=0);
    }

    @Test
    public void ifCooperator(){
        int number = affiliationDao.ifCooperator(1,2);
        assertTrue(number==0);
    }

    @Test
    public void getTotalPapers(){
        int number =affiliationDao.getTotalPapers(1);
        assertTrue(number>0);
    }

    @Test
    public void getTotalCitations(){
        int number = affiliationDao.getTotalCitations(1);
        assertTrue(number>0);
    }

    @Test
    public void getAuthors(){
        List<AuthorOrderInfo> list = affiliationDao.getAuthors(1);
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getAuthorId()>0);
        assertTrue(list.get(0).getCitation()>0);
        assertNotNull(list.get(0).getAuthorName());
    }

    @Test
    public void getPapers(){
        List<PaperOrderInfo> list = affiliationDao.getPapers(41);
        assertTrue(list.get(0).getPaperId()>0);
        assertTrue(list.get(0).getReference()>0);
        assertNotNull(list.get(0).getPaperName());
    }

    @Test
    public void getMeetings(){
        List<MeetingOrderInfo> list = affiliationDao.getMeetings(41);
        assertTrue(list.get(0).getCitation()>0);
        assertTrue(list.get(0).getPaperNumber()>0);
        assertNotEquals(list.get(0).getMeetingName(),"");
    }

    @Test
    public void number(){
        int count = affiliationDao.numberOfAffiliations();
        assertTrue(count>0);
    }

    @Test
    public void getByIdSuccess(){
        Affiliation affiliation = affiliationDao.getById(12);
        assertNotNull(affiliation.getAffiliationName());
    }

    @Test
    public void getByIdFailure(){
        Affiliation affiliation = affiliationDao.getById(10000);
        assertEquals(null,affiliation);
    }

    @Test
    public void orderByGIndex() {
        List<AffiliationOrderInfo> list = affiliationDao.orderByGIndex(1,10);
        assertNotNull(list.get(0).getAffiliationName());
        assertTrue(list.get(0).getPaperNumber()>0);
        for(int i=0;i<10;i++){
            System.out.println(list.get(i).getgIndex());
        }
    }
}