package com.example.domain;

public class AuthorOrderInfo {
    public int authorId;
    public String authorName;
    public int paperNumber;
    public double activation;
    public int citation;
    public int gIndex;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getPaperNumber() {
        return paperNumber;
    }

    public void setPaperNumber(int paperNumber) {
        this.paperNumber = paperNumber;
    }

    public int getCitation() {
        return citation;
    }

    public void setCitation(int citation) {
        this.citation = citation;
    }

    public double getActivation() {
        return activation;
    }
    public void setActivation(double activation) {
        this.activation = activation;
    }

    public int getgIndex() {
        return gIndex;
    }

    public void setgIndex(int gIndex) {
        this.gIndex = gIndex;
    }
}
