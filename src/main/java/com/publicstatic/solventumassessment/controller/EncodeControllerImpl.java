package com.publicstatic.solventumassessment.controller;

import com.publicstatic.solventumassessment.controller.model.ShortenRequest;
import com.publicstatic.solventumassessment.controller.model.ShortenResponse;
import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.service.EncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
@RequestMapping("encode")
public class EncodeControllerImpl implements EncodeController {

    @Autowired
    private EncodeService encodeService;

//    EncodeController(EncodeService encodeService) {
//        this.encodeService = encodeService;
//    }

    @GetMapping()
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

    @Override
    @PostMapping()
    public ShortenResponse encodeUrl(@RequestBody ShortenRequest encodeRequest) {
        String decodedUrl = encodeRequest.getUrl();
        ShortenResponse resp = new ShortenResponse(decodedUrl);
        try {
            resp.setEncodedUrl(encodeService.encodeUrl(decodedUrl));
        } catch (InvalidUrlException iue) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, decodedUrl + " is not a valid url.", iue);
        }
        return resp;
    }
}
