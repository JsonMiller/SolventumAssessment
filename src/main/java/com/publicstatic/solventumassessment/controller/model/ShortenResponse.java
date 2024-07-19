package com.publicstatic.solventumassessment.controller.model;

public class ShortenResponse {

    public ShortenResponse() {

    }

    public ShortenResponse(String decodedUrl) {
        this.decodedUrl = decodedUrl;
    }

    String encodedUrl;

    String decodedUrl;

    public String getEncodedUrl() {
        return encodedUrl;
    }

    public void setEncodedUrl(String encodedUrl) {
        this.encodedUrl = encodedUrl;
    }

    public String getDecodedUrl() {
        return decodedUrl;
    }

    public void setDecodedUrl(String decodedUrl) {
        this.decodedUrl = decodedUrl;
    }
}
