package com.publicstatic.solventumassessment.repository;

import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;

public interface EncodeRepository {

    boolean hasEncodedUrl(String encodedUrl);

    String getEncodedUrl(String decodedUrl);

    boolean hasDecodedUrl(String decodedUrl);

    String getDecodedUrl(String encodedUrl);

    void save(String decodedUrl, String encodedUrl) throws OutOfMapSpaceException;

    int size();
}
