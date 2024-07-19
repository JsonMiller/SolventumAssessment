package com.publicstatic.solventumassessment.controller;

public interface ShortUrlController {

    /**
     * Rest endpoint that should redirect to the decoded url
     * @param encodedUrlId the id of the encoded url
     */
    void resolveEncodedUrl(String encodedUrlId);
}
