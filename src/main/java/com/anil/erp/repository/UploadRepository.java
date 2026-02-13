package com.anil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.UploadEntity;



@Repository
public interface UploadRepository extends JpaRepository<UploadEntity, Long> {

}