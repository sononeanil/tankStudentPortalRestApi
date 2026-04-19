package com.anil.erp.pojo;

import java.util.List;
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
	private String profilePhoto;
	private List<String> coreExpertise;
	private List<String> subjectList;
	private List<String> standardList;
	private List<String> boardList;
	private List<String> preferableTimings;
	private String projectedNewBatch;
	private List<String> weeklyAvailability;
	private String description;
	private List<String> credentials;
	private List<String> qualifications;
	private String trialClassAvailable;
	private String gender;
	private List<String> languages;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String phoneNumber;
	private String emailId;
	private String password;
	private Map<String, Long> fees;














}
