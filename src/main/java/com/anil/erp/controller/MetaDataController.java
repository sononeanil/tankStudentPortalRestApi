package com.anil.erp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.MetaDataEntity;
import com.anil.erp.service.CustomerService;
import com.anil.erp.service.MetaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/erpsystem/metadata")
@CrossOrigin("*")
public class MetaDataController {
	
	@Autowired
	private MetaDataService metaDataService;
	
	@GetMapping("/all")
	public ResponseEntity<ErpsystemResponse> getMetaDataList() {
		
		return metaDataService.getMetaDataList();
	}
	
	@GetMapping("/")
	public ResponseEntity<ErpsystemResponse> getMetaDataList(@RequestParam String key) {
		System.out.println("Key=" + key);
		
		return metaDataService.getMetaDataListByKey(key);
	}
	
	@PostMapping("/")
	public ResponseEntity<ErpsystemResponse> createMetaData(@RequestBody MetaDataEntity metaDataEntity) {
		return metaDataService.createMetaData(metaDataEntity);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ErpsystemResponse> deleteMetaData(@PathVariable long id) {
		return metaDataService.deleteMetaData(id);
	}
	
}