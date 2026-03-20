package com.anil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.PublishCourseEntity;



@Repository
public interface PublishCourseRepository extends JpaRepository<PublishCourseEntity, Long> {

}