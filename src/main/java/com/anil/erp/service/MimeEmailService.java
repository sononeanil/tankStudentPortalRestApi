package com.anil.erp.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
public class MimeEmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    public MimeEmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlEmail(String to, String name, String courseName) throws Exception {

        // 1. Prepare HTML template
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("courseName", courseName);

        String html = templateEngine.process("RegisterCourse", context);

        // 2. Create MIME message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Course Registration Successful 🎉");
        helper.setText(html, true); // true = HTML

        // 3. Add inline image
        ClassPathResource image = new ClassPathResource("static/logo.png");
        helper.addInline("logoImage", image);

        // 4. Send email
        mailSender.send(message);
    }

}
