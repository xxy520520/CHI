package com.example.domain;

public class AffiliationOrderInfo {

    public int affiliationId;
    public String affiliationName;
    public int paperNumber;
    public double activation;
    public int citation;
    public int gIndex;

    public int getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(int affiliationId) {
        this.affiliationId = affiliationId;
    }

    public String getAffiliationName() {
        return affiliationName;
    }

    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
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
