package com.example.web;

import com.example.domain.MeetingMap;
import com.example.domain.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingControllerTest {
    @Autowired
    private MeetingController meetingController;

    @Test
    public void getMeetingMap() {
        Response re = meetingController.getMeetingMap("Automated Software Engineering (ASE)");
        MeetingMap meetingMap = (MeetingMap) re.getContent();
        assertNotNull(meetingMap.getName());
        assertTrue(meetingMap.getTotalCitations() > 0);
        assertTrue(meetingMap.getTotalPapers() > 0);
        assertNotNull(meetingMap.getAuthors().get(0));
        assertNotNull(meetingMap.getAffiliations().get(0));
    }
}
