package com.example.domain;

import java.util.List;

public class AffiliationNetwork {
    public List<Affiliation> nodes;
    public List<String> links;
    public List<Affiliation> getNodes() {
        return nodes;
    }

    public void setNodes(List<Affiliation> nodes) {
        this.nodes = nodes;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
