package com.example.service;

import com.example.dao.MeetingDao;
import com.example.domain.AuthorOrderInfo;
import com.example.domain.MeetingMap;
import com.example.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingDao meetingDao;

    public Response getMeetingMap(String name){
        try{
            MeetingMap meetingMap=new MeetingMap();
            meetingMap.setName(name);
            meetingMap.setTotalPapers(meetingDao.getTotalPapers(name));
            meetingMap.setTotalCitations(meetingDao.getTotalCitations(name));
            meetingMap.setAuthors(meetingDao.getAuthors(name));
            meetingMap.setAffiliations(meetingDao.getAffiliations(name));
            return Response.buildSuccess(meetingMap);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
