package com.example.domain;

public class Affiliation {

    public void setAffiliationId(int affiliationId) {
        this.affiliationId = affiliationId;
    }

    public int affiliationId;
    public String affiliationName;
    public double activation;

    public int getAffiliationId() {
        return affiliationId;
    }

    public String getAffiliationName() {
        return affiliationName;
    }

    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
    }

    public double getActivation() {
        return activation;
    }

    public void setActivation(double activation) {
        this.activation = activation;
    }
}
