package com.publicstatic.solventumassessment.service;

import com.publicstatic.solventumassessment.exceptions.InvalidIdException;
import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;

public interface EncodeService {

    /**
     * Encode a url and return back a encoded url
     * @param url valid url
     * @return encoded url
     * @throws InvalidUrlException if the given url is not formatted properly
     * @throws OutOfMapSpaceException if the data store is too full
     */
    String encodeUrl(String url) throws InvalidUrlException, OutOfMapSpaceException;

    /**
     *  decodes a url given an encoded url
     * @param url encoded url
     * @return the decoded url
     * @throws InvalidUrlException if the given encoded url is not properly formatted
     */
    String decodeUrl(String url) throws InvalidUrlException;

    /**
     * looks up a decoded url given an encoded id
     * @param id the id portion of an encoded url
     * @return the decoded url
     * @throws InvalidIdException if the id is not formatted properly or does not exist.
     */
    String decodeId(String id) throws InvalidIdException;
}
