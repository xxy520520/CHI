package com.example.domain;

import java.util.List;

public class AuthorNetwork {
    public List<Author> nodes;
    public List<String> links;
    public List<Author> getNodes() {
        return nodes;
    }

    public void setNodes(List<Author> nodes) {
        this.nodes = nodes;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
