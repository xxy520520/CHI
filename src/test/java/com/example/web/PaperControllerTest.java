package com.example.web;

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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaperControllerTest {

    @Autowired
    PaperController paperController;

    @Test
    public void searchPaper() {
        Response re =paperController.searchPaper("Humanoid: A Deep Learning-Based Approach to Automated Black-box Android App Testing",null,null,null,null,null,null,1);
        ArrayList<Paper> paperList=(ArrayList<Paper>)re.getContent();
        int totalPage=Integer.valueOf(re.getMessage());
        assertTrue(paperList.size()>0);
        assertTrue(totalPage>=1);
    }

    @Test
    public void getDetails() {
        Response re =paperController.getDetails(1);
        Paper paper=(Paper)re.getContent();
        assertTrue(paper.getPaperId()==1);
        assertNotNull(paper.getPaperName());
    }

    @Test
    public void OrderByCitation(){
        Response re=paperController.orderByCitation(1);
        ArrayList<Paper> paperList=(ArrayList<Paper>)re.getContent();
        assertTrue(paperList.size()>0);
        assertTrue(Integer.valueOf(re.getMessage())>=1);
    }

    @Test
    public void upload(){
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
        Response response=paperController.upload(file);
        assertEquals("文件格式错误",response.getMessage());
    }
}