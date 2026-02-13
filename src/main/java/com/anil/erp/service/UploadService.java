package com.anil.erp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.UploadEntity;
import com.anil.erp.repository.CustomerRepository;
import com.anil.erp.repository.UploadRepository;

@Service
public class UploadService {
	
	@Autowired
	UploadRepository uploadRepository;
	
	public ResponseEntity<ErpsystemResponse> getUploadList() {
		String uploadDir = System.getProperty("user.dir") + "/uploads";
		Path uploadDirectoryPath = Paths.get(uploadDir);
//		System.out.println("uploadDirectoryPath" + uploadDirectoryPath);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		String message ="Uploaded File List";
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			List<String> uploadedFilesList = Files.list(uploadDirectoryPath).filter(Files :: isRegularFile)
			.map(file -> file.getFileName().toString())
			.collect(Collectors.toList());
			erpsystemResponse.getErpSystemResponse().put("uploadedFilesList", uploadedFilesList);
		}catch(IOException ioException) {
			httpStatus = HttpStatus.FORBIDDEN;
			message = "Directory does not exists or Dont have permission to read files";
			System.out.println(ioException.getLocalizedMessage());
		}
		erpsystemResponse.getErpSystemResponse().put("message", message);
//		erpsystemResponse.getErpSystemResponse()
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}
	
	public ResponseEntity<ErpsystemResponse> createUpload(UploadEntity uploadEntity) {
		uploadRepository.save(uploadEntity);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("createUpload", "Upload got Registred in the system");
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<ErpsystemResponse> deleteUpload(long uploadId) {
		uploadRepository.deleteById(uploadId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}
	
	public ResponseEntity<ErpsystemResponse> publishUpload(UploadEntity uploadEntity1) {
		UploadEntity uploadEntity = new UploadEntity();
		String uploadDir = System.getProperty("user.dir") + "/uploads/"; // project root
//		System.out.println("UPload Directory=" + uploadDir);
//
		String message  = "File Published successfully. " + uploadEntity.getFileName();
		HttpStatus httpStatus = HttpStatus.CREATED;
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		try {
			String publishDirectory = uploadEntity.getBoard() 
					+ "/" + uploadEntity.getStandard()
					+ "/" + uploadEntity.getTerm() 
					+ "/" + uploadEntity.getSubject() 
					+ "/" + uploadEntity.getType();
			System.out.println(publishDirectory + "/" + uploadEntity.getFileName());
            Path sourcePath = Paths.get(uploadDir, uploadEntity.getFileName());
            Path targetPath = Paths.get(publishDirectory, uploadEntity.getFileName());
            
           Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING	);
        }catch(MaxUploadSizeExceededException maxUploadSizeExceededException){
        	message = "File publish failed: ";
        	httpStatus = HttpStatus.CONTENT_TOO_LARGE;
        }
		catch (IOException e) {
            message = "File upload failed: " + e.getMessage();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
		erpsystemResponse.getErpSystemResponse().put("message", message);
		 return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}
	
}
