package com.anil.erp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.ZoomEntity;
import com.anil.erp.pojo.ZoomPOJO;
import com.anil.erp.service.ZoomService;
import com.anil.erp.util.ZoomSignatureUtil;

@RestController
@RequestMapping("/erpsystem/zoom")

public class ZoomController {
	
	@Autowired
	ZoomService zoomService;
	
    @GetMapping("/signature")
    public ResponseEntity<ErpsystemResponse> getSignature(@RequestParam String meetingNumber, @RequestParam int role) {
    	return zoomService.generateSignature(meetingNumber, role);
    }
    
    @PostMapping("/setup")
    public ResponseEntity<ErpsystemResponse> setupMeeting(@RequestBody ZoomPOJO zoomEntity){
    	System.out.println("Setting up zoom meeting");
    	return zoomService.createZoom(zoomEntity);
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<ErpsystemResponse> getUpcomingMeeting(){
    	System.out.println("getUpcomingMeeting up zoom meeting");
    	return zoomService.getUpcomingMeeting();
    }

}
