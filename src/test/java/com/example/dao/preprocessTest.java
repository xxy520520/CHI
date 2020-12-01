package com.example.dao;

import com.example.domain.PaperOrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class preprocessTest {
    @Test
    public void init(){
        try {
            preprocess.init();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findPaper(){
        boolean result=true;
        try {
            result=preprocess.findPaper("1");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(!result);
    }

    @Test
    public void getAuthorReference(){
        int[] result=new int[3];
        try {
            result=preprocess.getAuthorReference(1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result[0]>=0);
    }

    @Test
    public void getAuthorPapers(){
        double result=0;
        try {
            result=preprocess.getAuthorPapers(1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result>=0);
    }

    @Test
    public void getAffiliationReference(){
        int[] result=new int[3];
        try {
            result=preprocess.getAffiliationReference(1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result[0]>=0);
    }

    @Test
    public void getAffiliationPapers(){
        double result=0;
        try {
            result=preprocess.getAffiliationPapers(1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result>=0);
    }

    @Test
    public void getFieldReference(){
        int[] result=new int[3];
        try {
            result=preprocess.getFieldReference(1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result[0]>=0);
    }

    @Test
    public void getFieldPapers(){
        double result=0;
        try {
            result=preprocess.getFieldPapers(1);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result>=0);
    }


    @Test
    public void calculateAuthorsGIndex() {
        //double result=0;
        try {
            preprocess.calculateAuthorsGIndex();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        //assertTrue(result>=0);

    }
}
