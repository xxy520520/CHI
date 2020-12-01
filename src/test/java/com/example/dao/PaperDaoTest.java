package com.example.dao;

import com.example.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaperDaoTest {
    @Autowired
    PaperDao paperDao;

    @Test
    public void orderByReference(){
        List<PaperOrderInfo> list = paperDao.orderByReference(1,10);
        assertTrue(list.get(0).getPaperId()>0);
    }

    @Test
    public void searchNumber(){
        int count = paperDao.searchNumbers("u","","","","1900","2100","");
        assertTrue(count>=0);
    }

    @Test
    public void searchNumber1(){
        int count = paperDao.searchNumbers("a","b","c","d","1900","2100","e");
        assertTrue(count>=0);
    }


    @Test
    public void number(){
        int count = paperDao.numberOfPapers();
        assertTrue(count>=0);
    }

    @Test
    public void getPaperDetailsById1(){
        Paper paper = paperDao.getPaperDetailsById(3);
        assertEquals(3,paper.getPaperId());
        assertNotNull(paper.getPaperName());
        assertNotNull(paper.getAuthors());
        assertNotNull(paper.getAbstra());
    }

    @Test
    public void getPaperDetailsById2(){
        Paper paper = paperDao.getPaperDetailsById(100000);
        assertEquals(null,paper);
    }

    @Test
    public void getAuthors(){
        List<Author> authors = paperDao.getAuthors(3);
        assertNotNull(authors.get(0).getAuthorName());
        assertTrue(authors.get(0).getAuthorId()>0);
    }

    @Test
    public void getAffiliations(){
        List<Affiliation> affiliations = paperDao.getAffiliations(3);
        assertNotNull(affiliations.get(0).getAffiliationName());
        assertTrue(affiliations.get(0).getAffiliationId()>0);
    }

    @Test
    public void getFields(){
        List<Field> fields = paperDao.getFields(61);
        assertNotNull(fields.get(0).getFieldName());
        assertTrue(fields.get(0).getFieldId()>0);
    }

    @Test
    public void search(){
        List<Paper> paper = paperDao.search("","","","","1900","2100","",1,10);
        assertNotNull(paper.get(0).getAffiliations());
        assertTrue(paper.get(0).getPaperId()>=0);
    }

    @Test
    public void search1(){
        List<Paper> paper = paperDao.search("u","","","","1900","2100","",1,10);
        assertNotNull(paper.get(0).getAffiliations());
        assertTrue(paper.get(0).getPaperId()>=0);
    }

    @Test
    public void search2(){
        List<Paper> paper = paperDao.search("","Y. Guo","","","1900","2100","",1,10);
        assertTrue(paper.get(0).getPaperId()>=0);
    }

    @Test
    public void search3(){
        List<Paper> paper = paperDao.search("","","Nanjing University","","1900","2100","",1,10);
        assertTrue(paper.get(0).getPaperId()>=0);
    }

    @Test
    public void search4(){
        List<Paper> paper = paperDao.search("","","","i","1900","2100","",1,10);
        assertTrue(paper.get(0).getPaperId()>=0);
    }

    @Test
    public void search5(){
        List<Paper> paper = paperDao.search("","","","","1900","2100","js",1,10);
        assertTrue(paper.get(0).getPaperId()>=0);
    }

    @Test
    public void search6(){
        List<Paper> paper = paperDao.search("","","","","2018","2019","",1,10);
        assertTrue(paper.get(0).getPaperId()>=0);
    }

    @Test
    public void search7(){
        List<Paper> paper = paperDao.search("","Y. Guo","","","2018","2018","",1,10);
        assertEquals(0,paper.size());
    }

    /*@Test
    public void processTest(){
        String path = ".\\src\\main\\schema\\ase13_15_16_17_19.csv";
        try {
            preprocess.process(path);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(1,1);
    }*/
}