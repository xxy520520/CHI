package com.example.domain;

import java.util.List;

public class AuthorMap {
    public Author author;
    public String mainMeeting;
    public Affiliation nowAffiliation;
    public PaperOrderInfo representPaper;
    public int totalPapers;
    public int totalCitations;
    public List<Field> representFields;
    public List<Field> nowFields;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getMainMeeting() {
        return mainMeeting;
    }

    public void setMainMeeting(String mainMeeting) {
        this.mainMeeting = mainMeeting;
    }

    public Affiliation getNowAffiliation() {
        return nowAffiliation;
    }

    public void setNowAffiliation(Affiliation nowAffiliation) {
        this.nowAffiliation = nowAffiliation;
    }

    public PaperOrderInfo getRepresentPaper() {
        return representPaper;
    }

    public void setRepresentPaper(PaperOrderInfo representPaper) {
        this.representPaper = representPaper;
    }

    public int getTotalPapers() {
        return totalPapers;
    }

    public void setTotalPapers(int totalPapers) {
        this.totalPapers = totalPapers;
    }

    public int getTotalCitations() {
        return totalCitations;
    }

    public void setTotalCitations(int totalCitations) {
        this.totalCitations = totalCitations;
    }

    public List<Field> getRepresentFields() {
        return representFields;
    }

    public void setRepresentFields(List<Field> representFields) {
        this.representFields = representFields;
    }

    public List<Field> getNowFields() {
        return nowFields;
    }

    public void setNowFields(List<Field> nowFields) {
        this.nowFields = nowFields;
    }
}
