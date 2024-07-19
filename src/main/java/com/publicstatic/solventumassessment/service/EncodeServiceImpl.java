package com.publicstatic.solventumassessment.service;

import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;
import com.publicstatic.solventumassessment.repository.EncodeRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Service
public class EncodeServiceImpl implements EncodeService {

    private final UrlValidator urlValidator;

    private final EncodeRepository encodeDao;

    private final RandomStringGenerator randomStringGenerator;

    private Pattern encodedUrlPattern;

    @Value("${encoded.url.hostname}")
    private String hostname;

    @Value("${encoded.url.port}")
    private String port;

    @Value("${encoded.url.protocol}")
    private String protocol;

    EncodeServiceImpl(EncodeRepository encodeDao) {
        String[] schemes = {"http", "https"};
        this.urlValidator = new UrlValidator(schemes);
        this.encodeDao = encodeDao;
        this.randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .get();

    }

    @Override
    public String encodeUrl(String url) throws InvalidUrlException, OutOfMapSpaceException {
        boolean valueFound = false;
        String encodedUrl = "";

        if(!this.urlValidator.isValid(url)) {
            throw new InvalidUrlException();
        }

        if(encodeDao.hasDecodedUrl(url)) {
            return encodeDao.getEncodedUrl(url);
        }

        while(!valueFound) {
            encodedUrl = generateEncodedUrl();
            valueFound = !encodeDao.hasEncodedUrl(encodedUrl);
        }

        encodeDao.save(url, encodedUrl);

        return encodedUrl;
    }

    @Override
    public String decodeUrl(String url) throws InvalidUrlException {
        if(!isValidEncodedUrl(url)) {
            throw new InvalidUrlException();
        }

        return encodeDao.getDecodedUrl(url);
    }

    private String generateEncodedUrl() {
        return protocol + "://" + hostname + ":" + port + "/" + randomStringGenerator.generate(7);
    }

    private boolean isValidEncodedUrl(String encodedUrl) {
        if(this.encodedUrlPattern == null) {
            this.encodedUrlPattern = Pattern.compile("^" + this.protocol + "://" + this.hostname + ":" + this.port + "/([A-Za-z0-9]{7})$");
        }
        return this.encodedUrlPattern.matcher(encodedUrl).matches();
    }
}
