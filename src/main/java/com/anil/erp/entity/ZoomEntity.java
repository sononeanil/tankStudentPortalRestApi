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
@Table(name = "zoommeeting")

public class ZoomEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String meetingId;
	private String meetingTopic;
	private String startTime;
	private String duration;
	private String password;
	private String organizerEmail;
	@Column(columnDefinition = "TEXT")
	private String joinUrl;
	@Column(columnDefinition = "TEXT")
	private String startUrl;

}
//-----------------------------------

