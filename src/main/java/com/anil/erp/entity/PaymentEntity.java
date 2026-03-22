//Jay Shree Ganeshaya Namah:

//-------------POJO Generator----------------------
package com.anil.erp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")

public class PaymentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String courseId;
	private String studentEmailId;
	private long amount;
	private String orderId;
	private String teachersEmailId;
	private String currency;
	private String paymentId;
	private String status;
	private LocalDateTime createdAt = LocalDateTime.now();
	@Transient
	private String signature;

}

