package com.anil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.AdminEntity;



@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

}