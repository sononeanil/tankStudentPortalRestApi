package com.anil.erp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.AdminEntity;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.repository.AdminRepository;
import com.anil.erp.repository.CustomerRepository;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	public ResponseEntity<ErpsystemResponse> getAdminList() {
		List<AdminEntity> lstAdmin = adminRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("adminList", lstAdmin);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
		
	}
	
	public ResponseEntity<ErpsystemResponse> createAdmin(AdminEntity adminEntity) {
		adminRepository.save(adminEntity);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("createAdmin", "Admin got Registred in the system");
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<ErpsystemResponse> deleteAdmin(long adminId) {
		adminRepository.deleteById(adminId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}
	
}
