package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.StudentEntity;
import com.anil.erp.pojo.StudentPOJO;
import com.anil.erp.service.CustomerService;
import com.anil.erp.service.StudentService;

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
@RequestMapping("/erpsystem/student")
@CrossOrigin("*")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getStudentList() {
		
		return studentService.getStudentList();
	}
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/parent/{id}")
	public ResponseEntity<ErpsystemResponse> getStudentListForParent(@PathVariable Long id) {
		
		return studentService.getStudentListForParent(id);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@PostMapping()
	public ResponseEntity<ErpsystemResponse> createStudent(@RequestBody StudentPOJO studentPOJO) {
		return studentService.createStudent(studentPOJO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deleteStudent(@PathVariable long id) {
		return studentService.deleteStudent(id);
	}
	
}