package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.LoginEntity;
import com.anil.erp.entity.RegisterCourseEntity;
import com.anil.erp.service.CustomerService;
import com.anil.erp.service.LoginService;
import com.anil.erp.service.PublishCourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/erpsystem/login")
@CrossOrigin("*")
public class LoginController {
	
	@Autowired
	private PublishCourseService publishCourseService;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getLoginList() {
		
		return loginService.getLoginList();
	}
	
	@PostMapping("/validate")
	public ResponseEntity<ErpsystemResponse> createLogin(@RequestBody LoginEntity loginEntity) {
		return loginService.createLogin(loginEntity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deleteLogin(@PathVariable long id) {
		return loginService.deleteLogin(id);
	}
	
	@GetMapping("/publishCourse/all")
	public ResponseEntity<ErpsystemResponse> getPublishCourseList() {
		return publishCourseService.getPublishCourseList();
	}
	
	@GetMapping("/publishCourse/top6")
	public ResponseEntity<ErpsystemResponse> getPublishCourseListTop6() {
		return publishCourseService.getPublishCourseListTop6();
	}
	
	@GetMapping("/publishCourse")
	public ResponseEntity<ErpsystemResponse> getCourseDetails(@RequestParam long courseId	) {
		return publishCourseService.getCourseDetails(courseId);
	}
	
	@PostMapping("/publishCourse/register")
	public ResponseEntity<ErpsystemResponse> registerCourse(@RequestBody RegisterCourseEntity registerCourseEntity) {
		return publishCourseService.registerCourse(registerCourseEntity);
	}

	
	
}