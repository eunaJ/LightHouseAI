package com.mju.lighthouseai.global.naversearch;

import java.net.URI;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NaverSearchService {

    private static final String CLIENT_ID = "Fq1fiDJdX9mxjfqjGh78";
    private static final String CLIENT_SECRET = "FH75vZ_w6L";

    public List<NaverSearchItem> searchLocal(String query) {
        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/")
            .path("v1/search/local.json")
            .queryParam("query", query)
            .queryParam("display", 5)
            .queryParam("start", 1)
            .queryParam("sort", "random")
            .encode()
            .build()
            .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
            .get(uri)
            .header("X-Naver-Client-Id", CLIENT_ID)
            .header("X-Naver-Client-Secret", CLIENT_SECRET)
            .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        NaverSearchResponse response;
        try {
            response = objectMapper.readValue(result.getBody(), NaverSearchResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }

        return response.items();
    }
}
