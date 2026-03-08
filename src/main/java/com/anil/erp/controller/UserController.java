package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.UserEntity;
import com.anil.erp.pojo.StudentPOJO;
import com.anil.erp.service.CustomerService;
import com.anil.erp.service.StudentService;
import com.anil.erp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/erpsystem/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getUserList() {
		
		return userService.getUserList();
	}
	
	@PostMapping("/signup")
	public ResponseEntity<ErpsystemResponse> createUser(@RequestBody UserEntity userEntity) {
		return userService.createUser(userEntity);
	}
	
	@GetMapping("userDetails/{emailId}")
	public void getUserDetails() {
		
	}
	

	

	@GetMapping("/parent/{id}/students")
	public ResponseEntity<ErpsystemResponse> getStudentListForParent(@PathVariable Long id) {
		
		return studentService.getStudentListForParent(id);
	}
	
	@PostMapping("/parent/enrollStudent")
	public ResponseEntity<ErpsystemResponse> createStudent(@RequestBody StudentPOJO studentPOJO) {
		return studentService.createStudent(studentPOJO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deleteStudent(@PathVariable long id) {
		return studentService.deleteStudent(id);
	}
	
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<ErpsystemResponse> deleteUser(@PathVariable long id) {
		return userService.deleteUser(id);
	}
	
}