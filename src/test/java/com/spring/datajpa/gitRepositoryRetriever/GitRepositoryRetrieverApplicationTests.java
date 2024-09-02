package com.spring.datajpa.gitRepositoryRetriever;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = GitRepositoryRetrieverApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class GitRepositoryRetrieverApplicationTests {
    @LocalServerPort
    private int port;
    HttpHeaders headers = new HttpHeaders();
    TestRestTemplate restTemplate = new TestRestTemplate();
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testGetRepositoryDetails_Success() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/repositories/shweta18tanpure/GitRepositoryManagement"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"fullName\":\"shweta18tanpure/GitRepositoryManagement\",\"description\":\"Restful API created using Java, Spring Boot, Spring Data JPA, RestTemplate and H2 Database " +
                "to retrieve git repository details from GIT API and save in DB\",\"cloneUrl\":\"https://github.com/shweta18tanpure/GitRepositoryManagement.git\",\"stars\":0,\"createdAt\":\"2024-08-21T15:55:53\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetRepositoryDetails_404_NotFound() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/repositories/TEST/TestRepo"),
                HttpMethod.GET, entity, String.class);
        String expected = "{\"message\":\"Not Found\",\"status\":\"404\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGetRepositoryDetails_405_IncorrectHttpMethod() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/repositories/TEST/TestRepo"),
                HttpMethod.POST, entity, String.class);
        String expected = "{\"status\":405,\"error\":\"Method Not Allowed\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }


}
