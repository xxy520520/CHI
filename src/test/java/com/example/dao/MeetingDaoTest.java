package com.example.dao;

import com.example.domain.AffiliationOrderInfo;
import com.example.domain.AuthorOrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingDaoTest {
    @Autowired
    MeetingDao meetingDao;

    @Test
    public void getTotalPapers(){
        int number = meetingDao.getTotalPapers("Automated Software Engineering (ASE)");
        assertTrue(number>0);
    }

    @Test
    public void getTotalCitations(){
        int number = meetingDao.getTotalCitations("Automated Software Engineering (ASE)");
        assertTrue(number>0);
    }

    @Test
    public void getAffiliations(){
        List<AffiliationOrderInfo> list = meetingDao.getAffiliations("Automated Software Engineering (ASE)");
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getAffiliationId()>0);
        assertNotNull(list.get(0).getAffiliationName());
    }

    @Test
    public void getAuthors(){
        List<AuthorOrderInfo> list = meetingDao.getAuthors("Automated Software Engineering (ASE)");
        assertTrue(list.get(0).getPaperNumber()>0);
        assertTrue(list.get(0).getAuthorId()>0);
        assertNotNull(list.get(0).getAuthorName());
    }
}
