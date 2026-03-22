package com.anil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.PaymentEntity;



@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}