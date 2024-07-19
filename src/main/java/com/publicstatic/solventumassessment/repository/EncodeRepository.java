package com.publicstatic.solventumassessment.repository;

import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;

public interface EncodeRepository {

    /**
     * Checks the data store to see if the encoded url id exists already.
     * @param encodedUrlId The id part of the encoded url
     * @return boolean indicating if the id exists or not.
     */
    boolean hasEncodedUrlId(String encodedUrlId);

    /**
     * Gets the encoded url from the data store from the decoded url.
     * @param decodedUrl the decoded url
     * @return the encoded url
     */
    String getEncodedUrl(String decodedUrl);

    /**
     * Check if the decoded url exists in the data store.
     * @param decodedUrl the decoded url
     * @return boolean indicating if the url exists or not.
     */
    boolean hasDecodedUrl(String decodedUrl);

    /**
     * get the decoded url from the data store by the encoded id.
     * @param encodedUrlId id from the encoded url
     * @return the decoded url
     */
    String getDecodedUrl(String encodedUrlId);

    /**
     * save a mapping in the data store that links the encoded url id to the decoded url.
     * @param encodedUrlId id of the encoded url
     * @param decodedUrl the decoded url
     * @throws OutOfMapSpaceException if we reached out storage limit complain loudly
     */
    void save(String encodedUrlId, String decodedUrl) throws OutOfMapSpaceException;

    /**
     * get the size of the currently stored element.
     * @return size stored in the data store.
     */
    int size();
}
