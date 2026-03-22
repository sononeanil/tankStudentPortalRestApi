package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.PaymentEntity;
import com.anil.erp.service.CustomerService;
import com.anil.erp.service.MetaDataService;
import com.anil.erp.service.PaymentService;

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
@RequestMapping("/erpsystem/payment")
@CrossOrigin("*")
public class PaymentController {

	
	@Autowired
	private PaymentService paymentService;


	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getPaymentList() {
		return paymentService.getPaymentList();
	}
	
	@PostMapping("/")
	public ResponseEntity<ErpsystemResponse> createPayment(@RequestBody PaymentEntity PaymentEntity) {
		return paymentService.createPayment(PaymentEntity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deletePayment(@PathVariable long id) {
		return paymentService.deletePayment(id);
	}
	
	@PostMapping("/generatePaymentOrder")
	public ResponseEntity<ErpsystemResponse> generatePaymentOrder(@RequestBody long paymentAmount) {
		return paymentService.generatePaymentOrder(paymentAmount);
	}
	
	@PostMapping("/verifyPayment")
	public ResponseEntity<ErpsystemResponse> verifyPayment(@RequestBody PaymentEntity PaymentEntity) {
		return paymentService.verifyPayment(PaymentEntity);
	}
	
}