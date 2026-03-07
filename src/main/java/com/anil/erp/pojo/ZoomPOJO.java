//Jay Shree Ganeshaya Namah:

//-------------POJO Generator----------------------
package com.anil.erp.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "details")

public class ZoomPOJO {
	
	private String meetingTopic;
	private String startTime;
	private String duration;
	private String password;
	private String organizerEmail;



}
//-----------------------------------

