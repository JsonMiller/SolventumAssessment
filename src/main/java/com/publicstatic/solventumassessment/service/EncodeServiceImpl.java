package com.publicstatic.solventumassessment.service;

import com.publicstatic.solventumassessment.exceptions.InvalidIdException;
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
        String encodedUrlId = "";

        if(!this.urlValidator.isValid(url)) {
            throw new InvalidUrlException();
        }

        if(encodeDao.hasDecodedUrl(url)) {
            return encodeDao.getEncodedUrl(url);
        }

        while(!valueFound) {
            encodedUrlId = generateEncodedUrlId();
            valueFound = !encodeDao.hasEncodedUrlId(encodedUrlId);
        }

        encodedUrl = generateEncodedUrl(encodedUrlId);

        encodeDao.save(encodedUrlId, url);

        return encodedUrl;
    }

    @Override
    public String decodeUrl(String url) throws InvalidUrlException {
        if(!isValidEncodedUrl(url)) {
            throw new InvalidUrlException();
        }

        String encodedUrlId = getEncodedUrlId(url);

        return encodeDao.getDecodedUrl(encodedUrlId);
    }

    @Override
    public String decodeId(String id) throws InvalidIdException {
        if(!encodeDao.hasEncodedUrlId(id)) {
            throw new InvalidIdException();
        }
        return encodeDao.getDecodedUrl(id);
    }

    private String generateEncodedUrlId() {
        return randomStringGenerator.generate(7);
    }

    private String generateEncodedUrl(String id) {
        return protocol + "://" + hostname + ":" + port + "/" + id;
    }

    private boolean isValidEncodedUrl(String encodedUrl) {
        if(this.encodedUrlPattern == null) {
            this.encodedUrlPattern = Pattern.compile("^" + this.protocol + "://" + this.hostname + ":" + this.port + "/([A-Za-z0-9]{7})$");
        }
        return this.encodedUrlPattern.matcher(encodedUrl).matches();
    }

    private String getEncodedUrlId(String encodedUrl) {
        return encodedUrl.substring(encodedUrl.length() - 7);
    }
}
