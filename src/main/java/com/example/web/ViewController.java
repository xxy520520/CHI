package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
    @RequestMapping(value = "/login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @RequestMapping(value = "/paperList")
    public String getPaperList() {
        return "paperList";
    }

    @RequestMapping(value = "/paperDetail")
    public String getPaperDetail(@RequestParam int paperId) {
        return "paperDetail";
    }

    @RequestMapping(value = "/paperOrder")
    public String getPaperOrder() {
        return "paperOrder";
    }

    @RequestMapping(value = "/authorOrder")
    public String getAuthorOrder() {
        return "authorOrder";
    }

    @RequestMapping(value = "/affiliationOrder")
    public String getAffiliationOrder() {
        return "affiliationOrder";
    }

    @RequestMapping(value = "/fieldOrder")
    public String getFieldOrder() {
        return "fieldOrder";
    }

    @RequestMapping(value = "/meetingMap")
    public String getMeetingMap() {
        return "meetingMap";
    }

    @RequestMapping(value = "/authorMap")
    public String getAuthorMap() {
        return "authorMap";
    }

    @RequestMapping(value = "/authorNetWork")
    public String getAuthorNetWork() {
        return "authorNetWork";
    }

    @RequestMapping(value = "/affiliationMap")
    public String getAffiliationMap() {
        return "affiliationMap";
    }

    @RequestMapping(value = "/fieldMap")
    public String getFieldMap() {
        return "fieldMap";
    }

    @RequestMapping(value="/searchPage")
    public String getSearchPage(){
        return "searchPage";
    }

}
