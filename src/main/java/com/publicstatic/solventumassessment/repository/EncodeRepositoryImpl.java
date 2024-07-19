package com.publicstatic.solventumassessment.repository;

import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Repository;

@Repository
public class EncodeRepositoryImpl implements EncodeRepository {

    /**
     * Using a bi-directional map so we can have easy lookups from
     * encoded or decoded urls - it's more memory intensive, but I think
     * performance is more important in the case.
     *
     * This will have the encoded url as the key and the decoded as the value.
     */
    private BidiMap<String, String> urlMap = new DualHashBidiMap<>();

    @Override
    public boolean hasEncodedUrl(String encodedUrl) {
        return urlMap.containsKey(encodedUrl);
    }

    @Override
    public String getEncodedUrl(String decodedUrl) {
        return urlMap.getKey(decodedUrl);
    }

    @Override
    public boolean hasDecodedUrl(String decodedUrl) {
        return urlMap.containsValue(decodedUrl);
    }

    @Override
    public String getDecodedUrl(String encodedUrl) {
        return urlMap.get(encodedUrl);
    }

    @Override
    public void save(String decodedUrl, String encodedUrl) throws OutOfMapSpaceException {
        if(urlMap.size() == Integer.MAX_VALUE) {
            throw new OutOfMapSpaceException();
        }
        urlMap.put(encodedUrl, decodedUrl);
    }

    @Override
    public int size() {
        return urlMap.size();
    }
}
