package com.anil.erp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.MetaDataEntity;
import com.anil.erp.repository.CustomerRepository;
import com.anil.erp.repository.MetaDataRepository;

@Service
public class MetaDataService {
	
	@Autowired
	MetaDataRepository metaDataRepository;
	
	public ResponseEntity<ErpsystemResponse> getMetaDataList() {
		List<MetaDataEntity> lstMetaData = metaDataRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("metaDataList", lstMetaData);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
	}
	
	public ResponseEntity<ErpsystemResponse> createMetaData(MetaDataEntity metaDataEntity) {
		metaDataRepository.save(metaDataEntity);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("createMetaData", "MetaData got Registred in the system");
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<ErpsystemResponse> deleteMetaData(long metaDataId) {
		metaDataRepository.deleteById(metaDataId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}

	public ResponseEntity<ErpsystemResponse> getMetaDataListByKey(String key) {
		List<MetaDataEntity> lstMetaData = metaDataRepository.findByKey(key);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("metaDataList", lstMetaData);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
	}
	
}
