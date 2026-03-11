package com.anil.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.service.ZoomService;

@RestController
@RequestMapping("/erpsystem/teacher")
public class TeacherController {
	@Autowired
	ZoomService zoomService;
	
	@GetMapping("/upcomingMeetings")
	public ResponseEntity<ErpsystemResponse> getUpcomingMeetingForTeacher(@RequestParam String teacherEmailId) {
		System.out.println("Getting upcoming meeting for " + teacherEmailId);
		return zoomService.getUpcomingMeetingForTeacher(teacherEmailId);
	}

}
