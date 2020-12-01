package com.example.service;


import com.example.domain.Paper;
import com.example.domain.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaperServiceTest {
    @Autowired
    private PaperService paperService;

    @Test
    public void upload1(){
        MultipartFile file=new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "a.excel";
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
        Response response=paperService.upload(file);
        assertEquals("文件格式错误",response.getMessage());
    }

    @Test
    public void upload2(){
        MultipartFile file=new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "a.excel";
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
        Response response=paperService.upload(file);
        assertEquals("该文件为空文件",response.getMessage());
    }

    @Test
    public void getDetails(){
        Response re = paperService.getDetails(61);
        Paper paper=(Paper)re.getContent();
        assertTrue(paper!=null);
        assertNotNull(paper.getAuthorList().get(0));
        assertNotNull(paper.getAffiliationList().get(0));
        assertNotNull(paper.getFieldList().get(0));
    }

    @Test
    public void searchPaper(){
        Response re =paperService.searchPaper("Humanoid: A Deep Learning-Based Approach to Automated Black-box Android App Testing",null,null,null,null,null,null,1,12);
        ArrayList<Paper> paperList=(ArrayList<Paper>)re.getContent();
        int totalPage=Integer.valueOf(re.getMessage());
        assertTrue(paperList.size()>0);
        assertTrue(totalPage>=1);
        assertTrue(paperList.get(0).getAuthorList().get(0).getAuthorId()>0);
    }

    @Test
    public void searchPaper2(){
        Response re =paperService.searchPaper(null,"b","c","d","e","2017","2019",1,12);
        ArrayList<Paper> paperList=(ArrayList<Paper>)re.getContent();
        int totalPage=Integer.valueOf(re.getMessage());
        assertTrue(paperList.size()>0);
        assertTrue(totalPage>=1);


    }

    @Test
    public void OrderByCitation(){

        Response re=paperService.orderByCitation(1,12);
        ArrayList<Paper> paperList=(ArrayList<Paper>)re.getContent();
        assertTrue(paperList.size()>0);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

}