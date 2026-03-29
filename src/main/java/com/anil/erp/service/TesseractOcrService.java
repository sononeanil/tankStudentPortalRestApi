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

	        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
	        tesseract.setLanguage("eng");

	        // ✅ NEW method
	        tesseract.setVariable("user_defined_dpi", "300");
	    }

	    public ResponseEntity<ErpsystemResponse> extractText(String imagePath) {
//	    	 String filePath = "/tmp/input.jpg";
	    	 String filePath = System.getProperty("java.io.tmpdir") + File.separator + "input.jpg";
	    	 System.out.println("222222");
		       
	    	String ocrText = null;
	        try {
	        	System.out.println("333333");
	        	 Files.copy(new URL("https://res.cloudinary.com/dtu5tquvo/image/upload/v1774765470/iwtycdqacrhfj8dhzbo7.jpg").openStream(), Paths.get(filePath));
	        	 System.out.println("4444");
	            ocrText =  tesseract.doOCR(new File(filePath));
	            System.out.println("55555 " + ocrText);
	        } catch (Exception e) {
	            throw new RuntimeException("OCR failed", e);
	        }
			String message  = "File Uploaded successfully. Please update rest of the attribute ";
			HttpStatus httpStatus = HttpStatus.CREATED;
			ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
	        erpsystemResponse.getErpSystemResponse().put("message", message);
			 return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	    }
	
	private  String getImageFromCloudinary(String imageUrl) {
	    try {
	        String filePath = "/tmp/input.jpg";
	        Files.copy(new URL(imageUrl).openStream(), Paths.get(filePath));
	        return filePath;
	    } catch (Exception e) {
	        throw new RuntimeException("Download failed", e);
	    }
	}
}
