package com.anil.erp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.AdminEntity;
import com.anil.erp.pojo.UserPOJO;
import com.anil.erp.service.AdminService;
import com.anil.erp.service.UserService;

@RestController
@RequestMapping("/erpsystem/admin")
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getAdminList() {
		return adminService.getAdminList();
	}
	
	@GetMapping("/userrole")
	public ResponseEntity<ErpsystemResponse> getUserRole(@RequestParam String userEmailId) {
		
		System.out.println("getUserRole userEmailId==>" + userEmailId);
		return userService.getUserRole(userEmailId);
	}
	
	@GetMapping("/user")
	public ResponseEntity<ErpsystemResponse> getUser() {
		return userService.getUserList();
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<ErpsystemResponse> deleteUser(@RequestParam int userId) {
		System.out.println("Deleting user=" + userId);
		return userService.deleteUser(userId);
	}
	
	@PutMapping("/userrole/update")
	public ResponseEntity<ErpsystemResponse> updateUserRole(@RequestBody UserPOJO userPOJO) {
		System.out.println("getUserId = " + userPOJO.getUserId() + "," + userPOJO.getRolesList());
		return userService.updateUserRole(userPOJO);
	}
	
	@PostMapping("/")
	public ResponseEntity<ErpsystemResponse> createAdmin(@RequestBody AdminEntity adminEntity) {
		return adminService.createAdmin(adminEntity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deleteAdmin(@PathVariable long id) {
		return adminService.deleteAdmin(id);
	}
	
}