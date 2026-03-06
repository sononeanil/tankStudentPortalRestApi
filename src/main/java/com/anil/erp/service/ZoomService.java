package com.anil.erp.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.*;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
@Service
public class ZoomService {

    private static final String SDK_KEY = "H_e5qgo0Q_Pnz3Yt5pMUQ";
    private static final String SDK_SECRET = "rUUQYG4bs3VtmCtnBawzfgouf6ex8nqn";

    public  ResponseEntity<ErpsystemResponse> generateSignature(String meetingNumber1, int role) {
    	
    	Map zoomMeeting = createMeeting();
    	String meetingNumber = String.valueOf(zoomMeeting.get("id")) ;
        long iat = System.currentTimeMillis() / 1000;
        long exp = iat + 60 * 60 * 2;

        Map<String, Object> payload = new HashMap<>();
        payload.put("sdkKey", SDK_KEY);
        payload.put("mn", meetingNumber);
        payload.put("role", role);
        payload.put("iat", iat);
        payload.put("exp", exp);
        payload.put("appKey", SDK_KEY);
        payload.put("tokenExp", exp);

        SecretKey key = Keys.hmacShaKeyFor(SDK_SECRET.getBytes());
        ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
        erpsystemResponse.getErpSystemResponse().put("signature", Jwts.builder()
                .setClaims(payload)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact());
        erpsystemResponse.getErpSystemResponse().put("meetingPassword", String.valueOf(zoomMeeting.get("password")));
        erpsystemResponse.getErpSystemResponse().put("meetingNumber", meetingNumber);

        return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
    }
    
    private Map createMeeting() {
    	  	String url = "https://api.zoom.us/v2/users/me/meetings";

    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setBearerAuth(generateAccessToken());
    	    headers.setContentType(MediaType.APPLICATION_JSON);

    	    Map<String, Object> body = new HashMap<>();
    	    body.put("topic", "React Embedded Meeting");
    	    body.put("type", 2);
    	    body.put("duration", 30);
    	    body.put("timezone", "Asia/Kolkata");
    	    
    	    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    	    RestTemplate restTemplate = new RestTemplate();

    	    ResponseEntity<Map> response =
    	            restTemplate.postForEntity(url, request, Map.class);

    	    Map data = response.getBody();
    	    return data;
    }
    
    private String generateAccessToken() {

        String url = "https://zoom.us/oauth/token?grant_type=account_credentials&account_id=0z2iIoYWTKKoXjrSbCcdzg";

        HttpHeaders headers = new HttpHeaders();

        String auth = Base64.getEncoder()
                .encodeToString(("F7FNelKFTgeHUo9alLQ" + ":" + "bJjvO7o4VbhmnDdebLuv1BTLPNIsc37t").getBytes());

        headers.set("Authorization", "Basic " + auth);

        HttpEntity<String> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        Map body = response.getBody();

        return (String) body.get("access_token");
    }
    
}