package com.anil.erp.service;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anil.erp.pojo.TutorBiographyPOJO;

@Service
public class PythonServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String pythonServiceUrl = "http://localhost:8000/generate-content";

    @Value("${aiService.url}")
    private String aiServiceUrl;
    
    public String generateAiSummary(String ocrText) {
        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("2222");
        ocrText = readTextFile();
        ocrText = ocrText.replace("\r", "")
                .replace("\n", " ")
                .replaceAll("\\s+", " ");
//        PromptRequest requestBody = new PromptRequest(ocrText);
        System.out.println("3333");
        // JSON body
        String jsonBody = "{ \"prompt_text\": \"" + ocrText.replace("\"", "\\\"") + "\" }";
        System.out.println("Json Body" + jsonBody);

        // Wrap in HttpEntity
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        // Send POST request
        String response = restTemplate.postForObject(pythonServiceUrl, request, String.class);
        System.out.println(response);
        return response; // JSON string: {"generated_text":"..."}
    }
    
    public static String readTextFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(".\\OCR.txt")));
//            System.out.println("Content Read" + content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public String createTutorBiography(TutorBiographyPOJO tutorBiographyPOJO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TutorBiographyPOJO> entity = new HttpEntity<>(tutorBiographyPOJO, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                aiServiceUrl + "/learningplatform/tutorbiography/createTutorBiography",
                entity,
                String.class
        );

        return response.getBody();
    }
    
}