package com.publicstatic.solventumassessment.service;

import com.publicstatic.solventumassessment.exceptions.InvalidUrlException;
import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;
import com.publicstatic.solventumassessment.repository.EncodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncodeServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(EncodeServiceTest.class);

    @Autowired
    private EncodeService encodeService;

    @MockBean
    private EncodeRepository encodeDao;

    @Test
    public void testEncodeSuccess() throws InvalidUrlException, OutOfMapSpaceException {
        String testUrl = "http://public-static.com/test";
        when(encodeDao.hasDecodedUrl(testUrl)).thenReturn(false);
        when(encodeDao.hasEncodedUrlId(Mockito.anyString())).thenReturn(false);

        String encodedUrl = encodeService.encodeUrl(testUrl);

        assertThat(encodedUrl).isNotBlank();
        assertThat(encodedUrl).isNotEqualTo(testUrl);
    }

    @Test
    public void testDuplicateEncodedUrl() throws InvalidUrlException, OutOfMapSpaceException {
        String testUrl = "http://public-static.com/test";
        when(encodeDao.hasDecodedUrl(testUrl)).thenReturn(false);
        when(encodeDao.hasEncodedUrlId(Mockito.anyString())).thenReturn(true).thenReturn(false);

        String encodedUrl = encodeService.encodeUrl(testUrl);

        assertThat(encodedUrl).isNotBlank();
        assertThat(encodedUrl).isNotEqualTo(testUrl);
    }

    @Test
    public void testDuplicateDecodedUrl() throws InvalidUrlException, OutOfMapSpaceException {
        String testUrl = "http://public-static.com/test";
        String savedEncodedUrl = "http://localhost/testing";
        when(encodeDao.hasDecodedUrl(testUrl)).thenReturn(true);
        when(encodeDao.getEncodedUrl(testUrl)).thenReturn(savedEncodedUrl);

        String encodedUrl = encodeService.encodeUrl(testUrl);

        assertThat(encodedUrl).isNotBlank();
        assertThat(encodedUrl).isNotEqualTo(testUrl);
        assertThat(encodedUrl).isEqualTo(savedEncodedUrl);
    }

    @Test
    public void testDecodeSuccess() throws InvalidUrlException {
        String testUrlId = "test123";
        String testUrl = "http://localhost:8080/" + testUrlId;
        String decodedUrl = "http://public-static.com/test";

        when(encodeDao.hasEncodedUrlId(testUrlId)).thenReturn(true);
        when(encodeDao.getDecodedUrl(testUrl)).thenReturn(decodedUrl);

        String resultUrl = encodeService.decodeUrl(testUrl);

        assertThat(resultUrl).isEqualTo(decodedUrl);
    }

    @Test
    public void testDecodeInvalidUrl() throws InvalidUrlException {
        String testUrl = "http://lowcalhorse:8080/test123";

        assertThrows(InvalidUrlException.class, () -> encodeService.decodeUrl(testUrl));
    }
}
