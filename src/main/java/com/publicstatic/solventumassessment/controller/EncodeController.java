package com.publicstatic.solventumassessment.controller;

import com.publicstatic.solventumassessment.controller.model.ShortenRequest;
import com.publicstatic.solventumassessment.controller.model.ShortenResponse;
import org.springframework.web.bind.annotation.*;

public interface EncodeController {

    /**
     * Rest endpoint to take in a valid url and return a shortened url... or atleast a proxy url.
     * @param encodeRequest request body expects a json object with a url property
     * @return The response should be a json object with an encoded url as well as the decoded url.
     */
    ShortenResponse encodeUrl(@RequestBody ShortenRequest encodeRequest);

    /**
     * Rest endpoing to take in a valid shortened url and return the decoded original url
     * @param decodeRequest request body expects a json object with a url property
     * @return The response should be a json object with a decoded url as well as the encoded url.
     */
    ShortenResponse decodeUrl(@RequestBody ShortenRequest decodeRequest);
}
