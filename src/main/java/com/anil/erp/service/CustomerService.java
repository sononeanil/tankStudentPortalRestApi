package com.anil.erp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	public ResponseEntity<ErpsystemResponse> getCustomerList() {
		List<CustomerEntity> lstCustomer = customerRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		
//		erpsystemResponse.getErpSystemResponse().put("customerList", new CustomerEntity());
//		erpsystemResponse.getErpSystemResponse().put("message", "Unable to fetch Records");
		erpsystemResponse.getErpSystemResponse().put("customerList", lstCustomer);
		
//		 return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
		
	}
	
	public ResponseEntity<ErpsystemResponse> createCustomer(CustomerEntity customerEntity) {
		customerRepository.save(customerEntity);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("createCustomer", "Customer got Registred in the system");
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<ErpsystemResponse> deleteCustomer(long customerId) {
		customerRepository.deleteById(customerId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
//		erpsystemResponse.getErpSystemResponse().put(null, erpsystemResponse)
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}
	
}
