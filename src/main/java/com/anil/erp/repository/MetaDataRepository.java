package com.anil.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anil.erp.entity.MetaDataEntity;

import com.anil.erp.entity.MetaDataEntity;



@Repository
public interface MetaDataRepository extends JpaRepository<MetaDataEntity, Long> {

	List<MetaDataEntity> findByKey(String key);

}