package com.example.domain;

import java.util.List;

public class MeetingMap {
    public String name;
    public int totalPapers;
    public int totalCitations;
    public List<AffiliationOrderInfo> affiliations;
    public List<AuthorOrderInfo> authors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
