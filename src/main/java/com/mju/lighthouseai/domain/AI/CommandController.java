package com.mju.lighthouseai.domain.AI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CommandController {
    @PostMapping("/api/v1/run_command")
    public String runCommand(@RequestBody String cmdJson) {
        RestTemplate restTemplate = new RestTemplate();

        // Set the headers for the HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //Set the Request Body with json Structure
        HttpEntity<String> entity = new HttpEntity<String>(cmdJson, headers);

        // Send the request to the python server
        String url = "http://localhost:5000/api/v1/run_command";
        String response = restTemplate.postForObject(url, entity, String.class);

        return response;
    }
}