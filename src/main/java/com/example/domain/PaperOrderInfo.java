package com.example.domain;

public class PaperOrderInfo {
    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int paperId;
    public String paperName;
    public int referenceNumber;

    public int getPaperId() {
        return paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getReference() {
        return referenceNumber;
    }

    public void setReference(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
