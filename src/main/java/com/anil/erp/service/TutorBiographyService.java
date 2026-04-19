package com.anil.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.controller.LoginController;
import com.anil.erp.entity.UserEntity;
import com.anil.erp.pojo.TutorBiographyEntity;
import com.anil.erp.pojo.TutorBiographyPOJO;

@Service
public class TutorBiographyService {

	@Autowired
	PythonServiceClient pythonServiceClient;

	@Autowired
	UserService userService;



	
	public ResponseEntity<ErpsystemResponse> getTutorBiographyList() {
//		List<TutorBiographyEntity> lstTutorBiography = tutorBiographyRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
//		erpsystemResponse.getErpSystemResponse().put("tutorBiographyList", lstTutorBiography);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
		
	}
	
	public ResponseEntity<ErpsystemResponse> createTutorBiography(TutorBiographyPOJO tutorBiographyPOJO) {
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;
		String message = "Successful";
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setAlternateEmailId(tutorBiographyPOJO.getEmailId()	);
			userEntity.setEmail(tutorBiographyPOJO.getUserId());
			userEntity.setPhoneNumber(tutorBiographyPOJO.getPhoneNumber());
			userEntity.setAddress(tutorBiographyPOJO.getState());
			userEntity.setCity(tutorBiographyPOJO.getCity());
			userEntity.setFirstName(tutorBiographyPOJO.getFirstName());
			userEntity.setLastName(tutorBiographyPOJO.getLastName());
			userEntity.setPassword(tutorBiographyPOJO.getPassword());
			
			userService.createUser(userEntity);
			
			
			tutorBiographyPOJO.setPassword(null);
			tutorBiographyPOJO.setUserId(null);
			
			pythonServiceClient.createTutorBiography(tutorBiographyPOJO);
			
			erpsystemResponse = new ErpsystemResponse();
			
			erpsystemResponse.getErpSystemResponse().put("createTutorBiography", "TutorBiography got Registred in the system");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpStatus = HttpStatus.BAD_REQUEST;
			message = "Exception while creating Biography of Tutor";
			
		}
		erpsystemResponse.getErpSystemResponse().put("message", message);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}

	public ResponseEntity<ErpsystemResponse> deleteTutorBiography(long tutorBiographyId) {
//		tutorBiographyRepository.deleteById(tutorBiographyId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}
	
}
