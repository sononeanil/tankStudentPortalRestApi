package com.anil.erp.service;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.util.ImagePreprocessor;

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
        tesseract.setPageSegMode(6);
        tesseract.setOcrEngineMode(1);
    }

    @Async
    public ResponseEntity<ErpsystemResponse> extractText() {

        String filePath = System.getProperty("java.io.tmpdir") 
                + File.separator 
                + "input_" + System.currentTimeMillis() + ".jpg";

        try {
        	System.out.println("TESSDATA_PREFIX=" + System.getenv("TESSDATA_PREFIX"));
            System.out.println("Downloading image...");
//            Files.copy(new URL("https://res.cloudinary.com/dtu5tquvo/image/upload/v1774791152/nthjhr2zuds5isftw4sl.jpg").openStream(), Paths.get(filePath));
            
            ImagePreprocessor imagePreprocessor = new ImagePreprocessor();
            
             
            Files.copy(
            	    new URL("https://res.cloudinary.com/dtu5tquvo/image/upload/v1774844107/eeuwgh1msh80wxsclxe0.jpg").openStream(),
            	    Paths.get(filePath),
            	    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            	);
            System.out.println("Preprocessing Started....");
            File preProecssedFile =   imagePreprocessor.preprocessImage(filePath);
            System.out.println("Running OCR...");
            String ocrText = tesseract.doOCR(preProecssedFile);

            System.out.println("OCR TEXT: " + ocrText);

            // Cleanup temp file
            new File(filePath).delete();
            preProecssedFile.delete();

            ErpsystemResponse response = new ErpsystemResponse();
            response.getErpSystemResponse().put("message", "OCR Success");
            response.getErpSystemResponse().put("text", ocrText);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("OCR failed", e);
        }
    }
    
    
    private ITesseract createTesseract() {
        Tesseract t = new Tesseract();
        t.setLanguage("eng");
        t.setPageSegMode(4); // better for your images
        t.setOcrEngineMode(1);
        t.setVariable("user_defined_dpi", "300");
        t.setVariable("preserve_interword_spaces", "1");
        return t;
    }
    
    @Async
    public void extractTextAsync() {

        String filePath = System.getProperty("java.io.tmpdir") 
                + File.separator 
                + "input_" + System.currentTimeMillis() + ".jpg";

        try {
            System.out.println("Downloading image...");

            Files.copy(
                new URL("https://res.cloudinary.com/dtu5tquvo/image/upload/v1774844107/eeuwgh1msh80wxsclxe0.jpg").openStream(),
                Paths.get(filePath),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            ImagePreprocessor imagePreprocessor = new ImagePreprocessor();

            System.out.println("Preprocessing Started....");
            File processedFile = imagePreprocessor.preprocessImage(filePath);

            System.out.println("Running OCR...");

            ITesseract tesseract = createTesseract();
            String ocrText = tesseract.doOCR(processedFile);

            System.out.println("OCR TEXT: " + ocrText);

            // Cleanup
            new File(filePath).delete();
            processedFile.delete();

            // 👉 TODO: Save result in DB instead of returning

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
