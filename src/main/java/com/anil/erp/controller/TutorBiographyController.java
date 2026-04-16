package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.pojo.TutorBiographyEntity;
import com.anil.erp.pojo.TutorBiographyPOJO;
import com.anil.erp.service.CustomerService;
import com.anil.erp.service.TutorBiographyService;

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
@RequestMapping("/erpsystem/tutor")
@CrossOrigin("*")
public class TutorBiographyController {
	
	@Autowired
	private TutorBiographyService tutorBiographyService;
	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getTutorBiographyList() {
		
		return tutorBiographyService.getTutorBiographyList();
	}
	
	@PostMapping("/enrolTutor")
	public ResponseEntity<ErpsystemResponse> createTutorBiography(@RequestBody TutorBiographyPOJO tutorBiographyEntity) {
		return tutorBiographyService.createTutorBiography(tutorBiographyEntity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deleteTutorBiography(@PathVariable long id) {
		return tutorBiographyService.deleteTutorBiography(id);
	}
	
}