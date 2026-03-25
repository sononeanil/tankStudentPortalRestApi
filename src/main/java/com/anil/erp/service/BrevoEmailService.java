package com.anil.erp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

@Service
public class BrevoEmailService {

//    private final String API_KEY = System.getenv("BREVO_API_KEY");
	  private final TemplateEngine templateEngine;

	    @Value("${brevo.api.key}")
	    private String API_KEY;
	    
    public BrevoEmailService( TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String toEmail, String userName, String courseName, String subjectLine ) {

        try {
        	
        	Context context = new Context();
            context.setVariable("name", userName);
            context.setVariable("courseName", courseName);
            ClassPathResource image = new ClassPathResource("static/logo.png");
            String htmlContent =  templateEngine.process("RegisterCourse", context);
            
            Map<String, Object> requestMap = new HashMap<>();

            Map<String, String> sender = new HashMap<>();
            sender.put("name", "Tank App");
            sender.put("email", "anilsonone@gmail.com");
            
            List<Map<String, String>> toList = new ArrayList<>();
            Map<String, String> to = new HashMap<>();
            to.put("email", toEmail);
            toList.add(to);
            
            requestMap.put("sender", sender);
            requestMap.put("to", toList);
            requestMap.put("subject", subjectLine);
            requestMap.put("htmlContent", htmlContent); 
            
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(requestMap);
            
            MediaType mediaType = MediaType.parse("application/json");
            
            RequestBody body = RequestBody.create(json, mediaType);
        	
//        	System.out.println("444444 " + toEmail + " --> " + API_KEY.substring(5,5));
            OkHttpClient client = new OkHttpClient();
            
//            System.out.println(json);

//            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url("https://api.brevo.com/v3/smtp/email")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("api-key", this.API_KEY)
                    .addHeader("content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println("Response: " + response.body().string());

            response.close(); // ✅ important

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}