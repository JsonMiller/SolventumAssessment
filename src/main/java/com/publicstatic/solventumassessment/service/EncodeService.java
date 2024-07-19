package com.publicstatic.solventumassessment.service;

import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;

public interface EncodeService {
    String encodeUrl(String url) throws InvalidUrlException, OutOfMapSpaceException;

    String decodeUrl(String url) throws InvalidUrlException;
}
