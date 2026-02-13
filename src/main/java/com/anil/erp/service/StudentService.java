package com.anil.erp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.StudentEntity;
import com.anil.erp.entity.UserEntity;
import com.anil.erp.pojo.StudentPOJO;
import com.anil.erp.repository.CustomerRepository;
import com.anil.erp.repository.StudentRepository;
import com.anil.erp.repository.UserRepository;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public ResponseEntity<ErpsystemResponse> getStudentList() {
		List<StudentEntity> lstStudent = studentRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("studentList", lstStudent);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
		
	}
	
	
	public ResponseEntity<ErpsystemResponse> getStudentListForParent(Long id) {
		List<StudentEntity> lstStudent = studentRepository.findAllByParentId(id);
//		List<StudentEntity> lstStudent = studentRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("studentList", lstStudent);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
		
	}
	
	public ResponseEntity<ErpsystemResponse> createStudent(StudentPOJO studentPOJO) {
		String message ="Student got Registred in the system";
		HttpStatus httpStatus = HttpStatus.CREATED;
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		
		try {
			/**
			 * copy only related properties from pojo to student entity
			 * the fields like loginId and password are not needed in studentEntity,
			 * so those fields will be omitted
			 * And then studentEntity will be saved to db 
			 */
			StudentEntity studentEntity = new StudentEntity();
			BeanUtils.copyProperties(studentPOJO, studentEntity, "loginId", "password");
			
			/**
			 * Once student is saved to db, then create a login entry in User Table
			 */
			studentRepository.save(studentEntity);
			/**
			 * Create a userEntity and save user entry for student in User DB
			 * Here we are saving student details in two tables 1. in Student table 2. User Table
			 * So that student and parent both can login to system individually
			 */
			UserEntity userEntity = new UserEntity();
			userEntity.setFirstName(studentPOJO.getFirstName());
			userEntity.setLastName(studentPOJO.getLastName());
			//In the userEntity, email is considered as login Id
			userEntity.setEmail(studentPOJO.getLoginId());
			userEntity.setPassword(studentPOJO.getPassword());
			userRepository.save(userEntity);
			
		}catch (DataIntegrityViolationException e) {
		message = "Error while registering the student " + e.getMostSpecificCause().getMessage();
		httpStatus = HttpStatus.BAD_REQUEST;
		
		}
 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		erpsystemResponse.getErpSystemResponse().put("message", message);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<ErpsystemResponse> deleteStudent(long studentId) {
		studentRepository.deleteById(studentId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}
	
}
