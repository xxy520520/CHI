package com.example.web;

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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AffiliationControllerTest {
    @Autowired
    AffiliationController affiliationController;

    @Test
    public void getAffiliationNetwork(){
        Response re=affiliationController.getAffiliationNetwork(12);
        AffiliationNetwork affiliationNetwork=(AffiliationNetwork) re.getContent();
        assertTrue(affiliationNetwork.getNodes().get(0).getAffiliationId()==12);
        assertTrue(affiliationNetwork.getLinks().get(0).equals("12,2,2"));
    }

    @Test
    public void orderByAmount() {
        Response re=affiliationController.orderByAmount(1);
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
        Response re=affiliationController.orderByActivation(1);
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
        Response re=affiliationController.orderByCitation(1);
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
        Response re = affiliationController.getAffiliationMap(1);
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
        Response re=affiliationController.orderByGIndex(1);
        ArrayList<AffiliationOrderInfo> authorInfo=(ArrayList<AffiliationOrderInfo>)re.getContent();
        assertTrue(authorInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }
}