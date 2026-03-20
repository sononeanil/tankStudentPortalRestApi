package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.PublishCourseEntity;
import com.anil.erp.service.CustomerService;
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
@RequestMapping("/erpsystem/teacher/course")
@CrossOrigin("*")
public class PublishCourseController {
	
	@Autowired
	private PublishCourseService publishCourseService;
	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getPublishCourseList() {
		
		return publishCourseService.getPublishCourseList();
	}
	
	@PostMapping("/publish")
	public ResponseEntity<ErpsystemResponse> createPublishCourse(@RequestBody PublishCourseEntity publishCourseEntity) {
		return publishCourseService.createPublishCourse(publishCourseEntity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deletePublishCourse(@PathVariable long id) {
		return publishCourseService.deletePublishCourse(id);
	}
	
}