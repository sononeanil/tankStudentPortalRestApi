package com.anil.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.PublishCourseEntity;



@Repository
public interface PublishCourseRepository extends JpaRepository<PublishCourseEntity, Long> {

	List<PublishCourseEntity> findTop6ByOrderByIdDesc();

}