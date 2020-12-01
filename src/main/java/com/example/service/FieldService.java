package com.example.service;

import com.example.dao.FieldDao;
import com.example.domain.FieldMap;
import com.example.domain.FieldOrderInfo;
import com.example.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldService {
    @Autowired
    private FieldDao fieldDao;

    public Response orderByAmount(int page, int offset){
        try{
            List<FieldOrderInfo> fieldList= fieldDao.orderByAmount(page,offset);
            ArrayList<FieldOrderInfo>ans=new ArrayList<FieldOrderInfo>(fieldList);

            int fieldNumber=fieldDao.numberOfFields();
            int totalPage=(fieldNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByCitation(int page, int offset){
        try{

            List<FieldOrderInfo> fieldList= fieldDao.orderByCitation(page,offset);
            ArrayList<FieldOrderInfo>ans=new ArrayList<FieldOrderInfo>(fieldList);

            int fieldNumber=fieldDao.numberOfFields();
            int totalPage=(fieldNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByActivation(int page, int offset){
        try{
            List<FieldOrderInfo> fieldList= fieldDao.orderByActivation(page,offset);
            ArrayList<FieldOrderInfo>ans=new ArrayList<FieldOrderInfo>(fieldList);
            int fieldNumber=fieldDao.numberOfFields();
            int totalPage=(fieldNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response getFieldMap(int id){
        try{
            FieldMap fieldMap=new FieldMap();
            fieldMap.setField(fieldDao.getById(id));
            fieldMap.setTotalPapers(fieldDao.getTotalPapers(id));
            fieldMap.setTotalCitations(fieldDao.getTotalCitations(id));
            fieldMap.setAuthors(fieldDao.getAuthors(id));
            fieldMap.setAffiliations(fieldDao.getAffiliations(id));
            fieldMap.setPapers(fieldDao.getPapers(id));
            fieldMap.setMeetings(fieldDao.getMeetings(id));
            return Response.buildSuccess(fieldMap);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
