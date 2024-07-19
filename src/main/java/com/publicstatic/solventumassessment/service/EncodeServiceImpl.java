package com.publicstatic.solventumassessment.service;

import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class EncodeServiceImpl implements EncodeService {

    private final UrlValidator urlValidator;

    EncodeServiceImpl() {
        String[] schemes = {"http", "https"};
        this.urlValidator = new UrlValidator(schemes);
    }

    public String encodeUrl(String url) throws InvalidUrlException {
        if(!this.urlValidator.isValid(url)) {
            throw new InvalidUrlException();
        }

        return url;
    }
}
