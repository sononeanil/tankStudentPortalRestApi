//Jay Shree Ganeshaya Namah:

//-------------POJO Generator----------------------
package com.anil.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "customer")

public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = true)
	private String firstName;
	@Column(nullable = true)
	private String lastName;
	@Column(nullable = true)
	private String address;
	@Column(nullable = true)
	private String city;
	@Column(nullable = true)
	private String country;
	@Column(nullable = true)
	private String newsLetter;
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private String role;
	private String phoneNumber;
	@Column(nullable = true)
	private String alternateEmailId;

}
//-----------------------------------

