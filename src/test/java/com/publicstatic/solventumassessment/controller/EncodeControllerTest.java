package com.publicstatic.solventumassessment.controller;

import com.publicstatic.solventumassessment.controller.model.ShortenRequest;
import com.publicstatic.solventumassessment.controller.model.ShortenResponse;
import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.service.EncodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncodeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private EncodeService mockService;

    @Test
    void encodeSuccess() throws Exception {
        ShortenRequest req = new ShortenRequest();
        String testUrl = "http://google.com";
        String testEncoded = "http://localhost:" + port + "/testing";
        req.setUrl(testUrl);

        when(mockService.encodeUrl(testUrl)).thenReturn(testEncoded);


        ShortenResponse resp = this.restTemplate.postForObject("http://localhost:" + port + "/encode", req, ShortenResponse.class);

        assertThat(resp).isNotNull();
        assertThat(resp.getDecodedUrl()).isEqualTo(testUrl);
    }

    @Test
    void encodeBadUrl() throws Exception {
        ShortenRequest req = new ShortenRequest();
        String testUrl = "This is just a string lul.";
        req.setUrl(testUrl);

        when(this.mockService.encodeUrl(testUrl)).thenThrow(new InvalidUrlException());

        ResponseEntity<ShortenResponse> resp = this.restTemplate.postForEntity("http://localhost:" + port + "/encode", req, ShortenResponse.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
