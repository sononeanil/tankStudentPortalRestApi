//Jay Shree Ganeshaya Namah:

//-------------POJO Generator----------------------
package com.anil.erp.entity;

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
@Table(name = "course")

public class PublishCourseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String courseName;
	private String specificTopic;
	private String batchStartTime;
	private int numberOfDays;
	private int numberOfHourse;
	private String description;
	private String targatedAudience;
	private String modeOfDelivery;
	private double fee;
	private String prerequisite;
	private String organizerEmailId;

}
//-----------------------------------

