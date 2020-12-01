package com.example.service;

import com.example.dao.PaperDao;
import com.example.domain.Paper;
import com.example.domain.PaperOrderInfo;
import com.example.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.preprocess;
import com.example.dao.spider;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaperService {
    @Autowired
    private PaperDao paperDao;

    public Response upload(MultipartFile file){
        String upload_folder = ".\\src\\main\\schema\\";
        if (file.isEmpty()) {
            return Response.buildFailure("该文件为空文件");
        }
        String filename = file.getOriginalFilename();
        if(filename.length()<5|| !filename.substring(filename.length()-4).equals(".csv")){
            return Response.buildFailure("文件格式错误");
        }
        try {
            // 获取文件并保存到指定文件夹中
            byte[] bytes = file.getBytes();
            Path path = Paths.get(upload_folder + filename);
            Files.write(path, bytes);
            preprocess.process(upload_folder + filename);
            return Response.buildSuccess();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (SQLException s){
            s.printStackTrace();
        }
        return Response.buildSuccess();
    }

    public Response crawler(String database,String conference,String beginYear,String endYear){
        String result="";
        try {
            result=spider.spider(database,conference,beginYear,endYear);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Response.buildSuccess(result);
    }

    public Response getDetails(int paperId){
        try {
            Paper ans = paperDao.getPaperDetailsById(paperId);
            ans.setAffiliationList(paperDao.getAffiliations(paperId));
            ans.setAuthorList(paperDao.getAuthors(paperId));
            ans.setFieldList(paperDao.getFields(paperId));
            return Response.buildSuccess(ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Response searchPaper(String paperName, String author, String affiliation, String publicationTitle, String keywords, String beginYear,String endYear, int currentPage, int offset){
        try{
            //当查询的属性值为null时，属性值可以为任意字符串，将其改为""
            paperName=paperName==null?"":paperName;
            author=author==null? "":author;
            affiliation=affiliation==null?"":affiliation;
            publicationTitle=publicationTitle==null? "":publicationTitle;
            keywords=keywords==null?"":keywords;
            beginYear=beginYear==null?"1500":beginYear;
            endYear=endYear==null?"2200":endYear;

            List<Paper>paperList=paperDao.search(paperName,author,affiliation,publicationTitle,beginYear,endYear,keywords,currentPage,offset);
            ArrayList<Paper>ans=new ArrayList<Paper>(paperList);
            for(int i=0;i<ans.size();i++){
                ans.get(i).setAffiliationList(paperDao.getAffiliations(ans.get(i).getPaperId()));
                ans.get(i).setAuthorList(paperDao.getAuthors(ans.get(i).getPaperId()));
                ans.get(i).setFieldList(paperDao.getFields(ans.get(i).getPaperId()));
            }
            int paperNumber=paperDao.searchNumbers(paperName,author,affiliation,publicationTitle,beginYear,endYear,keywords);
            int totalPage =(paperNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Response orderByCitation(int page, int offset){
        try{
            List paperOrderList=paperDao.orderByReference(page,offset);
            ArrayList<PaperOrderInfo>ans=new ArrayList(paperOrderList);
            //获取总页数
            int paperNumber=paperDao.numberOfPapers();
            int totalPage = (paperNumber-1)/offset+1;
            return Response.buildSuccess(Integer.toString(totalPage),ans);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
