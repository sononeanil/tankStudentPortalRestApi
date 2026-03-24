package com.anil.erp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ResendEmailService {
	
	
	
	private final WebClient webClient;
	@Value("${resend.api.key}")
    private String apiKey;
	
	  private final TemplateEngine templateEngine;

    public ResendEmailService(WebClient.Builder builder, TemplateEngine templateEngine) {
        this.webClient = builder.baseUrl("https://api.resend.com").build();
        this.templateEngine = templateEngine;
    }
    
    private String getHtmlMailBody(String name, String courseName) {
    	Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("courseName", courseName);
        ClassPathResource image = new ClassPathResource("static/logo.png");
        return templateEngine.process("RegisterCourse", context);

        // 2. Create MIME message
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);

      
        // 3. Add inline image
        
    }
    
    @Async
    public void sendEmail(String to, String subject) {

        Map<String, Object> body = Map.of(
                "from", "onboarding@resend.dev",  // default test sender
                "to", to,
                "subject", subject,
                "html", getHtmlMailBody(to, subject)
        );

        String response = "";
		try {
			response = webClient.post()
			        .uri("/emails")
			        .header("Authorization", "Bearer " + apiKey)
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(body)
			        .retrieve()
			        .bodyToMono(String.class)
			        .block();
		} catch (Exception e) {
			System.out.println("Exception while sending email 1111111 " + to);
			e.printStackTrace();
		}

        System.out.println("Email sent: 111111 " + response);
    }
	

}
