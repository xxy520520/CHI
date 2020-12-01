package com.example.service;

import com.example.domain.AffiliationMap;
import com.example.domain.AffiliationNetwork;
import com.example.domain.AffiliationOrderInfo;
import com.example.domain.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AffiliationServiceTest {
    //private final int OFF_SET=12;
   @Autowired
    AffiliationService affiliationService;

    @Test
    public void getAffiliationNetwork(){
        Response re=affiliationService.getAffiliationNetwork(12);
        AffiliationNetwork affiliationNetwork=(AffiliationNetwork) re.getContent();
        assertTrue(affiliationNetwork.getNodes().get(0).getAffiliationId()==12);
        assertTrue(affiliationNetwork.getLinks().get(0).equals("12,2,2"));
    }

    @Test
   public void orderByAmount() {
        Response re=affiliationService.orderByAmount(1,12);
        ArrayList<AffiliationOrderInfo>affList=(ArrayList<AffiliationOrderInfo>)re.getContent();
        System.out.println(affList.size());
        if(affList.size()!=0){
            assertEquals(false,affList.get(0).affiliationName.equals("NA"));
        }
        assertTrue(affList.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void orderByActivation() {
        Response re=affiliationService.orderByActivation(1,12);
        ArrayList<AffiliationOrderInfo>affList=(ArrayList<AffiliationOrderInfo>)re.getContent();
        System.out.println(affList.size());
        if(affList.size()!=0){
            assertEquals(false,affList.get(0).affiliationName.equals("NA"));
        }
        assertTrue(affList.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void orderByCitation() {
        Response re=affiliationService.orderByCitation(1,12);
        ArrayList<AffiliationOrderInfo>affList=(ArrayList<AffiliationOrderInfo>)re.getContent();
        System.out.println(affList.size());
        if(affList.size()!=0){
            assertEquals(false,affList.get(0).affiliationName.equals("NA"));
        }
        assertTrue(affList.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void getAffiliationMap() {
        Response re = affiliationService.getAffiliationMap(1);
        AffiliationMap affiliationMap = (AffiliationMap) re.getContent();
        assertNotNull(affiliationMap.getAffiliation());
        assertTrue(affiliationMap.getTotalCitations() > 0);
        assertTrue(affiliationMap.getTotalPapers() > 0);
        assertNotNull(affiliationMap.getAuthors().get(0));
        assertNotNull(affiliationMap.getPapers().get(0));
        assertNotNull(affiliationMap.getMeetings().get(0));
    }

    @Test
    public void orderByGIndex() {
        Response re=affiliationService.orderByGIndex(1,12);
        ArrayList<AffiliationOrderInfo> affiliationInfo=(ArrayList<AffiliationOrderInfo>)re.getContent();
        assertTrue(affiliationInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }
}

