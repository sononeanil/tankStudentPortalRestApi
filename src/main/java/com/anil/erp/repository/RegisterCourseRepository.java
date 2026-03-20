package com.anil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.RegisterCourseEntity;

@Repository
public interface RegisterCourseRepository extends JpaRepository<RegisterCourseEntity	, Long>{

}
