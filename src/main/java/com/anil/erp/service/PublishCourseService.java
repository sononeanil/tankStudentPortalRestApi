package com.anil.erp.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.PublishCourseEntity;
import com.anil.erp.entity.RegisterCourseEntity;
import com.anil.erp.entity.UserEntity;
import com.anil.erp.repository.CustomerRepository;
import com.anil.erp.repository.PublishCourseRepository;
import com.anil.erp.repository.RegisterCourseRepository;

@Service
public class PublishCourseService {
	
	@Autowired
	PublishCourseRepository publishCourseRepository;
	
	@Autowired
	RegisterCourseRepository registerCourseRepository;
	
	public ResponseEntity<ErpsystemResponse> getPublishCourseList() {
		List<PublishCourseEntity> lstPublishCourse = publishCourseRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("publishCourseList", lstPublishCourse);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
	}
	
	public ResponseEntity<ErpsystemResponse> getPublishCourseListTop6() {
		List<PublishCourseEntity> lstPublishCourse = publishCourseRepository.findTop6ByOrderByIdDesc();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("publishCourseListTop6", lstPublishCourse);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
	}
	
	public ResponseEntity<ErpsystemResponse> createPublishCourse(PublishCourseEntity publishCourseEntity) {
		publishCourseRepository.save(publishCourseEntity);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("createPublishCourse", "PublishCourse got Registred in the system");
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.CREATED);
	}
	
	

	public ResponseEntity<ErpsystemResponse> deletePublishCourse(long publishCourseId) {
		publishCourseRepository.deleteById(publishCourseId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}

	public ResponseEntity<ErpsystemResponse> getCourseDetails(long courseId) {
		Optional<PublishCourseEntity> courseDetails = publishCourseRepository.findById(courseId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("courseDetails", courseDetails);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse,HttpStatus.OK);
	}
	
	public ResponseEntity<ErpsystemResponse> registerCourse(RegisterCourseEntity registerCourseEntity) {
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;
		try {
			registerCourseRepository.save(registerCourseEntity);
			erpsystemResponse.getErpSystemResponse().put("message", "Course Registration Successful.");
		}catch(DataIntegrityViolationException dataIntegrityViolationException	) {
			erpsystemResponse.getErpSystemResponse().put("message", " Registration Failed.");
			httpStatus = HttpStatus.CONFLICT;
		}
		catch (Exception e) {
			erpsystemResponse.getErpSystemResponse().put("message", " Registration Failed. ");
			httpStatus = HttpStatus.CONFLICT;
		}
		
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}
	
}
