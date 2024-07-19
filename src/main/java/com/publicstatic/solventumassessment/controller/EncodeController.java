package com.publicstatic.solventumassessment.controller;

import com.publicstatic.solventumassessment.controller.model.ShortenRequest;
import com.publicstatic.solventumassessment.controller.model.ShortenResponse;
import org.springframework.web.bind.annotation.*;

public interface EncodeController {

    ShortenResponse encodeUrl(@RequestBody ShortenRequest encodeRequest);

    ShortenResponse decodeUrl(@RequestBody ShortenRequest decodeRequest);
}
