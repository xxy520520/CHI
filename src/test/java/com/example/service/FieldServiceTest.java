package com.example.service;

import com.example.domain.FieldMap;
import com.example.domain.FieldOrderInfo;
import com.example.domain.Response;
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
public class FieldServiceTest {
    @Autowired
    private FieldService fieldService;

    @Test
    public void orderByAmount(){
        Response re=fieldService.orderByAmount(1,12);
        ArrayList<FieldOrderInfo> fieldInfo=(ArrayList<FieldOrderInfo>)re.getContent();
        assertTrue(fieldInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void orderByActivation(){
        Response re=fieldService.orderByActivation(1,12);
        ArrayList<FieldOrderInfo> fieldInfo=(ArrayList<FieldOrderInfo>)re.getContent();
        assertTrue(fieldInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void orderByCitation(){
        Response re=fieldService.orderByCitation(1,12);
        ArrayList<FieldOrderInfo> fieldInfo=(ArrayList<FieldOrderInfo>)re.getContent();
        assertTrue(fieldInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void getFieldMap() {
        Response re = fieldService.getFieldMap(1);
        FieldMap fieldMap = (FieldMap) re.getContent();
        assertNotNull(fieldMap.getField());
        assertTrue(fieldMap.getTotalCitations() > 0);
        assertTrue(fieldMap.getTotalPapers() > 0);
        assertNotNull(fieldMap.getAuthors().get(0));
        assertNotNull(fieldMap.getAffiliations().get(0));
        assertNotNull(fieldMap.getPapers().get(0));
        assertNotNull(fieldMap.getMeetings().get(0));
    }

}