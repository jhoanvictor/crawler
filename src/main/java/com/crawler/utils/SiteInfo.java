package com.crawler.utils;

public class SiteInfo {

    private final String seletorTitulo;
    private final String tagLink;

    public SiteInfo(String seletorTitulo, String tagLink) {
        this.seletorTitulo = seletorTitulo;
        this.tagLink = tagLink;
    }

    public String getSeletorTitulo() {
        return seletorTitulo;
    }

    public String getTagLink() {
        return tagLink;
    }

}
