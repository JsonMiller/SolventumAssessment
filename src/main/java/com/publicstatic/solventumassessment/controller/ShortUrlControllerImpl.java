package com.publicstatic.solventumassessment.controller;

import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.service.EncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ShortUrlControllerImpl implements ShortUrlController {

    @Autowired
    private EncodeService encodeService;

    @Override
    @GetMapping("/{id}")
    public void resolveEncodedUrl(@PathVariable("id") String encodedUrlId) {
        try {
            encodeService.decodeUrl(encodedUrlId);
        } catch (InvalidUrlException e) {
            throw new RuntimeException(e);
        }
    }
}
