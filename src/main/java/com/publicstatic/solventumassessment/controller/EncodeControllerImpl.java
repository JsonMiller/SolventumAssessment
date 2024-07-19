package com.publicstatic.solventumassessment.controller;

import com.publicstatic.solventumassessment.controller.model.ShortenRequest;
import com.publicstatic.solventumassessment.controller.model.ShortenResponse;
import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;
import com.publicstatic.solventumassessment.service.EncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
public class EncodeControllerImpl implements EncodeController {

    @Autowired
    private EncodeService encodeService;

    @GetMapping()
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

    @Override
    @PostMapping("/encode")
    public ShortenResponse encodeUrl(@RequestBody ShortenRequest encodeRequest) {
        String decodedUrl = encodeRequest.getUrl();
        try {
            ShortenResponse resp = new ShortenResponse(decodedUrl);
            resp.setEncodedUrl(encodeService.encodeUrl(decodedUrl));
            return resp;
        } catch (InvalidUrlException iue) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, decodedUrl + " is not a valid url.", iue);
        } catch (OutOfMapSpaceException oom) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something awful happened.", oom);
        }

    }

    @Override
    @PostMapping("/decode")
    public ShortenResponse decodeUrl(ShortenRequest decodeRequest) {
        String encodedUrl = decodeRequest.getUrl();
        try {
            ShortenResponse resp = new ShortenResponse(encodeService.decodeUrl(encodedUrl));
            resp.setEncodedUrl(encodedUrl);
            return resp;
        } catch (InvalidUrlException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, encodedUrl + " is not a valid url.", e);
        }

    }
}
