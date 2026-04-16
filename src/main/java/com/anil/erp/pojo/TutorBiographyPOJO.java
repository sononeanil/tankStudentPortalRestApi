package com.anil.erp.pojo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TutorBiographyPOJO {

	private String firstName;
	private String lastName;
	private String userId;
	private String headline;
	private String coreExpertise;
	private String subjectList;
	private String standardList;
	private String preferableTimings;
	private String projectedNewBatch;
	private Map<String, Long> fees;
	private String description;
	private String credentials;
	private String fullName;
	private String profilePhoto;
	private String gender;
	private String city;
	private String state;
	private String languages;
	private String weeklyAvailability;
	private String trialClassAvailable;
	private String country;
	private String boardList;
	private String phoneNumber;
	private String emailId;
	private String pincode;
	private String qualifications;
	private String password;

}
