package com.example.domain;

import java.util.List;

public class Paper {
    public int paperId;
    public String paperName;
    public String authors;
    public String affiliations;
    public String publicationTitle;
    public String publishYear;
    public int startPage;
    public int endPage;
    public String abstra;      //摘要
    public String volume;
    public String doi;
    public String link;
    public String keywords;
    public String ieeeTerms;
    public String controlledTerms;
    public String nonControlledTerms;
    public String meshTerms;
    public int citation;
    public int reference;
    public String publisher;
    public String identifier;
    public List<Author> authorList;
    public List<Affiliation> affiliationList;
    public List<Field> fieldList;


    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
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

    public String getPaperName() {
        return paperName;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public String getAbstra() {
        return abstra;
    }

    public String getVolume() {
        return volume;
    }

    public String getDoi() {
        return doi;
    }

    public String getLink() {
        return link;
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

    public String getControlledTerms() {
        return controlledTerms;
    }

    public String getNonControlledTerms() {
        return nonControlledTerms;
    }

    public String getMeshTerms() {
        return meshTerms;
    }

    public int getCitation() {
        return citation;
    }

    public int getReference() {
        return reference;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setAbstra(String abstra) {
        this.abstra = abstra;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setIeeeTerms(String ieeeTerms) {
        this.ieeeTerms = ieeeTerms;
    }

    public void setControlledTerms(String controlledTerms) {
        this.controlledTerms = controlledTerms;
    }

    public void setNonControlledTerms(String nonControlledTerms) {
        this.nonControlledTerms = nonControlledTerms;
    }

    public void setMeshTerms(String meshTerms) {
        this.meshTerms = meshTerms;
    }

    public void setCitation(int citation) {
        this.citation = citation;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Affiliation> getAffiliationList() {
        return affiliationList;
    }

    public void setAffiliationList(List<Affiliation> affiliationList) {
        this.affiliationList = affiliationList;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }
}
