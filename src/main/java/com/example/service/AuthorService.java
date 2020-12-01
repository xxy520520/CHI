package com.example.service;

import com.example.dao.AuthorDao;
import com.example.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorDao authorDao;

    public Response orderByAmount(int page, int offset){
        try{
            List<AuthorOrderInfo> authorList= authorDao.orderByAmount(page,offset);
            ArrayList<AuthorOrderInfo>ans=new ArrayList<AuthorOrderInfo>(authorList);
            int authorNumber=authorDao.numberOfAuthors();
            int totalPage=(authorNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByGIndex(int page, int offset){
        try{
            List<AuthorOrderInfo> authorList= authorDao.orderByGIndex(page,offset);
            ArrayList<AuthorOrderInfo>ans=new ArrayList<AuthorOrderInfo>(authorList);
            int authorNumber=authorDao.numberOfAuthors();
            int totalPage=(authorNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response getAuthorNetwork(int id){
        try{
            List<Author> nodes= authorDao.getCooperators(id);
            AuthorNetwork authorNetwork=new AuthorNetwork();
            String link;
            List<String> links=new ArrayList<>();
            for(int i=0;i<nodes.size()-1;i++){
                for(int j=i+1;j<nodes.size();j++){
                    int result=authorDao.ifCooperator(nodes.get(i).getAuthorId(),nodes.get(j).getAuthorId());
                    if(result>0){
                        link=nodes.get(i).getAuthorId()+","+nodes.get(j).getAuthorId()+","+result;
                        links.add(link);
                    }
                }
            }
            authorNetwork.setNodes(nodes);
            authorNetwork.setLinks(links);
            return Response.buildSuccess(authorNetwork);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByCitation(int page, int offset){
        try{
            List<AuthorOrderInfo> authorList= authorDao.orderByCitation(page,offset);
            ArrayList<AuthorOrderInfo>ans=new ArrayList<AuthorOrderInfo>(authorList);

            int authorNumber=authorDao.numberOfAuthors();
            int totalPage=(authorNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response orderByActivation(int page, int offset){
        try{
            List<AuthorOrderInfo> authorList= authorDao.orderByActivation(page,offset);
            ArrayList<AuthorOrderInfo>ans=new ArrayList<AuthorOrderInfo>(authorList);

            int authorNumber=authorDao.numberOfAuthors();
            int totalPage=(authorNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response getAuthorMap(int id){
        try{
            AuthorMap authorMap=new AuthorMap();
            authorMap.setAuthor(authorDao.getById(id));
            authorMap.setTotalPapers(authorDao.getTotalPapers(id));
            authorMap.setTotalCitations(authorDao.getTotalCitations(id));
            authorMap.setMainMeeting(authorDao.getMainMeeting(id));
            authorMap.setNowAffiliation(authorDao.getNowAffiliation(id));
            authorMap.setRepresentPaper(authorDao.getRepresentPaper(id));
            authorMap.setRepresentFields(authorDao.getRepresentFields(id));
            authorMap.setNowFields(authorDao.getNowFields(id));
            return Response.buildSuccess(authorMap);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
