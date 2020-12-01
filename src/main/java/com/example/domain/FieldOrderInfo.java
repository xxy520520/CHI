package com.example.domain;

public class FieldOrderInfo {
    public int fieldId;
    public String fieldName;
    public int paperNumber;
    public double activation;
    public int citation;

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String authorName) {
        this.fieldName = authorName;
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
}
