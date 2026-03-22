package com.anil.erp.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.PaymentEntity;
import com.anil.erp.repository.CustomerRepository;
import com.anil.erp.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepository paymentRepository;
	
    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;
	
	public ResponseEntity<ErpsystemResponse> getPaymentList() {
		List<PaymentEntity> lstPayment = paymentRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("PaymentList", lstPayment);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
	}
	
	public ResponseEntity<ErpsystemResponse> createPayment(PaymentEntity PaymentEntity) {
		paymentRepository.save(PaymentEntity);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("createPayment", "Payment got Registred in the system");
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<ErpsystemResponse> deletePayment(long PaymentId) {
		paymentRepository.deleteById(PaymentId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}

	public ResponseEntity<ErpsystemResponse> generatePaymentOrder(long paymentAmount) {
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
//		erpsystemResponse.getErpSystemResponse().put("createPayment", "Payment got Registred in the system");
		RazorpayClient client;
		HttpStatus httpStatus = HttpStatus.OK;
		String message = "Successful";
		try {
			client = new RazorpayClient(keyId, keySecret);
			 JSONObject options = new JSONObject();
		        options.put("amount", paymentAmount * 100); // paise
		        options.put("currency", "INR");
		        options.put("receipt", "txn_" + System.currentTimeMillis());
		        Order order = client.orders.create(options);
		        erpsystemResponse.getErpSystemResponse().put("razorPayOrder", order.toString());
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			message = "RazorPay Exception";
			httpStatus = HttpStatus.BAD_GATEWAY;
			e.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
			message = "General Exception ";
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		erpsystemResponse.getErpSystemResponse().put("message", message);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}

	public ResponseEntity<ErpsystemResponse> verifyPayment(PaymentEntity paymentEntity) {
		
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		String message = "Successful";
		HttpStatus httpStatus = HttpStatus.OK;
		String actualSignature = "";
		try {
			actualSignature = Utils.getHash(paymentEntity.getOrderId() + "|" + paymentEntity.getPaymentId(), keySecret);
		} catch (RazorpayException e) {
			message = "Unable to Create Signature";
			httpStatus = HttpStatus.BAD_GATEWAY;
			e.printStackTrace();
		}

	    if (actualSignature.equals(paymentEntity.getSignature())) {
	        // ✅ Save in DB
	       httpStatus = HttpStatus.OK;
	    } else {
	        message = "Signature Invalid. Payment Failed";
	    }
	    
	    erpsystemResponse.getErpSystemResponse().put("message", message);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}
	
	
	
}
