package com.anil.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.ZoomEntity;



@Repository
public interface ZoomRepository extends JpaRepository<ZoomEntity, Long> {

	List<ZoomEntity> findAllByOrganizerEmail(String teacherEmailId);

}