package com.publicstatic.solventumassessment.controller;

import com.publicstatic.solventumassessment.exceptions.InvalidIdException;
import com.publicstatic.solventumassessment.service.EncodeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ShortUrlControllerImpl implements ShortUrlController {

    @Autowired
    private EncodeService encodeService;

    @Override
    @GetMapping("/{id}")
    public void resolveEncodedUrl(@PathVariable("id") String encodedUrlId, HttpServletResponse response) {
        try {
            String decodedUrl = encodeService.decodeId(encodedUrlId);
            response.setHeader("Location", decodedUrl);
            response.setStatus(302);
        } catch (InvalidIdException e) {
            throw new RuntimeException(e);
        }
    }
}
