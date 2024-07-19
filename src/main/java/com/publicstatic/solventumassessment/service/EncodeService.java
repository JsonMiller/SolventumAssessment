package com.publicstatic.solventumassessment.service;

import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;

public interface EncodeService {
    String encodeUrl(String url) throws InvalidUrlException;
}
