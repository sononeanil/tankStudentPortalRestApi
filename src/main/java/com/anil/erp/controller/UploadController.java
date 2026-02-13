package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.StudentEntity;
import com.anil.erp.entity.UploadEntity;
import com.anil.erp.pojo.UploadPOJO;
import com.anil.erp.service.CustomerService;
import com.anil.erp.service.UploadService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/erpsystem/upload")
@CrossOrigin("*")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getUploadList() {
		
		return uploadService.getUploadList();
	}
	/**
	 * Intentionally written logic of upload here.
	 * Will move it in some time later
	 * 
	 * @param file
	 * @return
	 */
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@PostMapping()
	public ResponseEntity<ErpsystemResponse> createUpload(@RequestParam("file") MultipartFile file, @RequestParam("destinationDirectory") String destinationDirectory) {
		String uploadDir = System.getProperty("user.dir") + "/uploads/" + destinationDirectory+"/"; // project root
//		System.out.println("UPload Directory=" + uploadDir);
//
		String message  = "File Uploaded successfully. Please update rest of the attribute " + file.getOriginalFilename();
		HttpStatus httpStatus = HttpStatus.CREATED;
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		try {
            // Save file to local directory
            
            File targetFile = new File(uploadDir + file.getOriginalFilename());
            System.out.println(targetFile.getCanonicalPath());
            file.transferTo(targetFile);
           
        }catch(MaxUploadSizeExceededException maxUploadSizeExceededException){
        	message = "File upload failed: " + file.getOriginalFilename() + " Maximum file size exceeded. Upload only up to 5MB size";
        	httpStatus = HttpStatus.CONTENT_TOO_LARGE;
        }
		catch (IOException e) {
            message = "File upload failed: " + file.getOriginalFilename() + e.getMessage();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
		erpsystemResponse.getErpSystemResponse().put("message", message);
		 return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@PostMapping("/publishUpload")
	public ResponseEntity<ErpsystemResponse> publishUpload(@RequestBody UploadPOJO uploadPOJO) {
		
		System.out.println(uploadPOJO + "1111111111111111111");
		return uploadService.publishUpload(null);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deleteUpload(@PathVariable long id) {
		return uploadService.deleteUpload(id);
	}
	
}