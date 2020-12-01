package com.example.web;

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
public class FieldControllerTest {
    @Autowired
    private FieldController fieldController;
    @Test
    public void orderByAmount() {
        Response re=fieldController.orderByAmount(1);
        ArrayList<FieldOrderInfo> fieldInfo=(ArrayList<FieldOrderInfo>)re.getContent();
        assertTrue(fieldInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);

    }
    @Test
    public void orderByCitation() {
        Response re=fieldController.orderByCitation(1);
        ArrayList<FieldOrderInfo> fieldInfo=(ArrayList<FieldOrderInfo>)re.getContent();
        assertTrue(fieldInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);

    }
    @Test
    public void orderByActivation() {
        Response re=fieldController.orderByActivation(1);
        ArrayList<FieldOrderInfo> fieldInfo=(ArrayList<FieldOrderInfo>)re.getContent();
        assertTrue(fieldInfo.size()==12);
        assertTrue(Integer.valueOf(re.getMessage())>=1);

    }

    @Test
    public void getFieldMap() {
        Response re = fieldController.getFieldMap(1);
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