package com.example.web;

import com.example.domain.Response;
import com.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PaperController {
    @Autowired
    private PaperService paperService;

    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public Response upload(@RequestParam("file") MultipartFile file){
        return paperService.upload(file);
    }


    @RequestMapping(value="/crawler",method = RequestMethod.POST)
    public Response crawler(@RequestParam("database") String database,@RequestParam("conference") String conference,
                            @RequestParam("beginYear") String beginYear,@RequestParam("endYear") String endYear){
        System.out.println(database+" begin");
        database=database==""?"ACM":database;
        conference=conference==""?"ASE":conference;
        beginYear=beginYear==""?"2013":beginYear;
        endYear=endYear==""?"2013":endYear;
        return paperService.crawler(database,conference,beginYear,endYear);
    }

    @RequestMapping(value="/api/paper/paperDetail",method = RequestMethod.GET)
    public Response getDetails(@RequestParam int paperId){
        return paperService.getDetails(paperId);
    }

    @RequestMapping(value="/api/paper/searchPaper",method=RequestMethod.GET)
    public Response searchPaper(@RequestParam String paperName,@RequestParam String author,@RequestParam String affiliation,@RequestParam String publicationTitle,@RequestParam String keywords,@RequestParam String beginYear,@RequestParam String endYear,@RequestParam int currentPage){
        paperName=paperName==""?null:paperName;
        author=author==""?null:author;
        affiliation=affiliation==""?null:affiliation;
        publicationTitle=publicationTitle==""?null:publicationTitle;
        keywords=keywords==""?null:keywords;
        beginYear=beginYear==""?null:beginYear;
        endYear=endYear==""?null:endYear;
        return paperService.searchPaper(paperName,author,affiliation,publicationTitle,keywords,beginYear,endYear,currentPage,12);
    }

    @RequestMapping(value="/api/paper/orderByCitation",method=RequestMethod.GET)
    public Response orderByCitation(@RequestParam int page){
        return paperService.orderByCitation(page,12);
    }

}
