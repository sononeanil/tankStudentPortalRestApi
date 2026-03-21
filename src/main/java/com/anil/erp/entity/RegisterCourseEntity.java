package com.anil.erp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RegisterCourse",
uniqueConstraints = {
		@UniqueConstraint(columnNames = {"courseId", "organizerEmailId", "studentEmailId"})
}
)

public class RegisterCourseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long courseId;
	private String organizerEmailId;
	private String studentEmailId;

}
