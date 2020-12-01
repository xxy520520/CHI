package com.example.domain;

public class SpiderModel {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(String affiliations) {
        this.affiliations = affiliations;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getDate_Add() {
        return Date_Add;
    }

    public void setDate_Add(String date_Add) {
        Date_Add = date_Add;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getAbstract_word() {
        return abstract_word;
    }

    public void setAbstract_word(String abstract_word) {
        this.abstract_word = abstract_word;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getFuding() {
        return fuding;
    }

    public void setFuding(String fuding) {
        this.fuding = fuding;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIeeeTerms() {
        return ieeeTerms;
    }

    public void setIeeeTerms(String ieeeTerms) {
        this.ieeeTerms = ieeeTerms;
    }

    public String getController() {
        return Controller;
    }

    public void setController(String controller) {
        Controller = controller;
    }

    public String getNonControlled() {
        return NonControlled;
    }

    public void setNonControlled(String nonControlled) {
        NonControlled = nonControlled;
    }

    public String getMesh() {
        return mesh;
    }

    public void setMesh(String mesh) {
        this.mesh = mesh;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public String getReferenc() {
        return referenc;
    }

    public void setReferenc(String referenc) {
        this.referenc = referenc;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String toRow(){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                this.title,this.authors,this.affiliations,this.ptitle,this.Date_Add,this.year,this.volume,this.issue,
                this.start,this.end,this.abstract_word,this.issn,this.isbn,this.doi,this.fuding,this.link,this.keywords,
                this.ieeeTerms,this.Controller,this.NonControlled,this.mesh,this.citation,this.referenc,this.license,
                this.online,this.issueDate,this.meetingDate,this.publisher, this.identifier);
    }

    String title="\"\"";
    String authors="\"\"";
    String affiliations="\"\"";
    String ptitle="\"\"";
    String Date_Add="\"\"";
    String year="\"\"";
    String volume="\"\"";
    String issue="\"\"";
    String start="\"\"";
    String end="\"\"";
    String abstract_word="\"\"";
    String issn="\"\"";
    String isbn="\"\"";
    String doi="\"\"";
    String fuding="\"\"";
    String link="\"\"";
    String keywords="\"\"";
    String ieeeTerms="\"\"";
    String Controller="\"\"";
    String NonControlled="\"\"";
    String mesh="\"\"";
    String citation="\"\"";
    String referenc="\"\"";
    String license="\"\"";
    String online="\"\"";
    String issueDate="\"\"";
    String meetingDate="\"\"";
    String publisher="\"\"";
    String identifier="\"\"";
}
