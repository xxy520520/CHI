package com.example.domain;

public class Author {
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int authorId;
    public String authorName;
    public double activation;

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public double getActivation() {
        return activation;
    }

    public void setActivation(double activation) {
        this.activation = activation;
    }
}
