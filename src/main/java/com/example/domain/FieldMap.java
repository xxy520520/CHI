package com.example.domain;

import java.util.List;

public class FieldMap {
    public Field field;
    public int totalPapers;
    public int totalCitations;
    public List<AffiliationOrderInfo> affiliations;
    public List<AuthorOrderInfo> authors;
    public List<PaperOrderInfo> papers;
    public List<MeetingOrderInfo> meetings;
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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

    public List<AffiliationOrderInfo> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<AffiliationOrderInfo> affiliations) {
        this.affiliations = affiliations;
    }

    public List<AuthorOrderInfo> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorOrderInfo> authors) {
        this.authors = authors;
    }

    public List<PaperOrderInfo> getPapers() {
        return papers;
    }

    public void setPapers(List<PaperOrderInfo> papers) {
        this.papers = papers;
    }


    public List<MeetingOrderInfo> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<MeetingOrderInfo> meetings) {
        this.meetings = meetings;
    }
}
