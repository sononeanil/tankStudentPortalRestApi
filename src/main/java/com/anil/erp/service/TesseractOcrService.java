package com.anil.erp.service;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

@Service
public class TesseractOcrService {

    private final ITesseract tesseract;

    public TesseractOcrService() {
        tesseract = new Tesseract();

        // ❌ DO NOT set datapath (Docker will handle it)
        // tesseract.setDatapath(...);

        tesseract.setLanguage("eng");
        tesseract.setVariable("user_defined_dpi", "300");
        tesseract.setVariable("preserve_interword_spaces", "1");
    }

    public ResponseEntity<ErpsystemResponse> extractText() {

        String filePath = System.getProperty("java.io.tmpdir") 
                + File.separator 
                + "input_" + System.currentTimeMillis() + ".jpg";

        try {
        	System.out.println("TESSDATA_PREFIX=" + System.getenv("TESSDATA_PREFIX"));
            System.out.println("Downloading image...");
//            Files.copy(new URL("https://res.cloudinary.com/dtu5tquvo/image/upload/v1774791152/nthjhr2zuds5isftw4sl.jpg").openStream(), Paths.get(filePath));
            Files.copy(
            	    new URL("https://res.cloudinary.com/dtu5tquvo/image/upload/v1774791152/nthjhr2zuds5isftw4sl.jpg").openStream(),
            	    Paths.get(filePath),
            	    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            	);
            System.out.println("Running OCR...");
            String ocrText = tesseract.doOCR(new File(filePath));

            System.out.println("OCR TEXT: " + ocrText);

            // Cleanup temp file
            new File(filePath).delete();

            ErpsystemResponse response = new ErpsystemResponse();
            response.getErpSystemResponse().put("message", "OCR Success");
            response.getErpSystemResponse().put("text", ocrText);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException("OCR failed", e);
        }
    }
}
