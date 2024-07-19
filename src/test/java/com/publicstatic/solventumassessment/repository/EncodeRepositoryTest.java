package com.publicstatic.solventumassessment.repository;

import com.publicstatic.solventumassessment.exceptions.OutOfMapSpaceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EncodeRepositoryTest {

    @Autowired
    EncodeRepository encodeRepository;

    @Test
    public void testSaveAndRetrieveEncoded() throws OutOfMapSpaceException {
        String testUrl = "http://public-static.com/testing";
        String testEncodedId = "test123";
        assertThat(encodeRepository.hasDecodedUrl(testUrl)).isEqualTo(false);
        assertThat(encodeRepository.hasEncodedUrlId(testEncodedId)).isEqualTo(false);

        encodeRepository.save(testEncodedId, testUrl);

        assertThat(encodeRepository.hasDecodedUrl(testUrl)).isEqualTo(true);
        assertThat(encodeRepository.hasEncodedUrlId(testEncodedId)).isEqualTo(true);

        assertThat(encodeRepository.getEncodedUrl(testUrl)).isEqualTo(testEncodedId);
        assertThat(encodeRepository.getDecodedUrl(testEncodedId)).isEqualTo(testUrl);
    }

}
