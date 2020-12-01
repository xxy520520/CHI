package com.example.service;

import com.example.dao.AffiliationDao;
import com.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AffiliationService {
    @Autowired
    private AffiliationDao affiliationDao;

    public Response getAffiliationNetwork(int id){
        try{
            List<Affiliation> nodes= affiliationDao.getCooperators(id);
            AffiliationNetwork affiliationNetwork=new AffiliationNetwork();
            String link;
            List<String> links=new ArrayList<>();
            for(int i=0;i<nodes.size()-1;i++){
                for(int j=i+1;j<nodes.size();j++){
                    int result=affiliationDao.ifCooperator(nodes.get(i).getAffiliationId(),nodes.get(j).getAffiliationId());
                    if(result>0){
                        link=nodes.get(i).getAffiliationId()+","+nodes.get(j).getAffiliationId()+","+result;
                        links.add(link);
                    }
                }
            }
            affiliationNetwork.setNodes(nodes);
            affiliationNetwork.setLinks(links);
            return Response.buildSuccess(affiliationNetwork);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByAmount(int page, int offset){
        try{
            List<AffiliationOrderInfo>affiliationList= affiliationDao.orderByAmount(page,offset);
            ArrayList<AffiliationOrderInfo>ans=new ArrayList<AffiliationOrderInfo>(affiliationList);
            int affiliationNumber=affiliationDao.numberOfAffiliations();
            int totalPage=(affiliationNumber-1)/offset+1;
            //System.out.println("-------------------------------------"+ans.size());
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByGIndex(int page, int offset){
        try{
            List<AffiliationOrderInfo> affiliationList= affiliationDao.orderByGIndex(page,offset);
            ArrayList<AffiliationOrderInfo>ans=new ArrayList<AffiliationOrderInfo>(affiliationList);
            int authorNumber=affiliationDao.numberOfAffiliations();
            int totalPage=(authorNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByActivation(int page, int offset){
        try{
            List<AffiliationOrderInfo>affiliationList= affiliationDao.orderByActivation(page,offset);
            ArrayList<AffiliationOrderInfo>ans=new ArrayList<AffiliationOrderInfo>(affiliationList);
            int affiliationNumber=affiliationDao.numberOfAffiliations();
            int totalPage=(affiliationNumber-1)/offset+1;
            //System.out.println("-------------------------------------"+ans.size());
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByCitation(int page, int offset){
        try{
            List<AffiliationOrderInfo>affiliationList= affiliationDao.orderByCitation(page,offset);
            ArrayList<AffiliationOrderInfo>ans=new ArrayList<AffiliationOrderInfo>(affiliationList);
            int affiliationNumber=affiliationDao.numberOfAffiliations();
            int totalPage=(affiliationNumber-1)/offset+1;
            //System.out.println("-------------------------------------"+ans.size());
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response getAffiliationMap(int id){
        try{
            AffiliationMap affiliationMap=new AffiliationMap();
            affiliationMap.setAffiliation(affiliationDao.getById(id));
            affiliationMap.setTotalPapers(affiliationDao.getTotalPapers(id));
            affiliationMap.setTotalCitations(affiliationDao.getTotalCitations(id));
            affiliationMap.setAuthors(affiliationDao.getAuthors(id));
            affiliationMap.setPapers(affiliationDao.getPapers(id));
            affiliationMap.setMeetings(affiliationDao.getMeetings(id));
            return Response.buildSuccess(affiliationMap);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
