package com.anil.erp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.UserEntity;
import com.anil.erp.pojo.UserPOJO;
import com.anil.erp.repository.MetaDataRepository;
import com.anil.erp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MetaDataRepository metaDataRepository;
	
	/**
	 * 
	 * @param userEmailId
	 * @return UserPojo with the list of roles which the user has
	 * Also return total  list of roles available in the metadata
	 * 
	 */
	public ResponseEntity<ErpsystemResponse> getUserRole(String userEmailId) {
	Optional<UserEntity> useEntityOptional =	userRepository.getByEmail(userEmailId);
	UserPOJO userPOJO = new UserPOJO();
	ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
	HttpStatus httpStatus = HttpStatus.OK;
	if(useEntityOptional.isPresent()) {
		UserEntity userEntity = useEntityOptional.get();
		userPOJO = new UserPOJO(userEntity.getId(), userEntity.getRole());
		erpsystemResponse.getErpSystemResponse().put("userPOJO", userPOJO);
	}else {
		erpsystemResponse.getErpSystemResponse().put("message", "Invalid email, User Not found");
		httpStatus = HttpStatus.NOT_FOUND;
	}
//	ROLE_LIST
	
	try {
		erpsystemResponse.getErpSystemResponse().put("lstMetaData", metaDataRepository.findByKey("ROLE_LIST"));
	}catch(Exception e) {
		e.printStackTrace();
	}
	System.out.println(httpStatus.value());
	return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}
	
	public ResponseEntity<ErpsystemResponse> getUserList() {
		List<UserEntity> lstUser = userRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("userList", lstUser);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
	}
	
	public ResponseEntity<ErpsystemResponse> createUser(UserEntity userEntity) {
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		HttpStatus httpStatus = HttpStatus.CREATED;
		try {
			userRepository.save(userEntity);
			erpsystemResponse.getErpSystemResponse().put("createUser", "User got Registred in the system");
		}catch(DataIntegrityViolationException dataIntegrityViolationException	) {
			erpsystemResponse.getErpSystemResponse().put("message", " Registration Failed. Email Id already taken. Please use another emaild id");
			httpStatus = HttpStatus.CONFLICT;
		}
		catch (Exception e) {
			erpsystemResponse.getErpSystemResponse().put("message", " Registration Failed. ");
			httpStatus = HttpStatus.CONFLICT;
		}
		
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}

	public ResponseEntity<ErpsystemResponse> deleteUser(long userId) {
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		String message = "User Deleted Successfully";
		
		try {
			userRepository.deleteById(userId);
		}catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			emptyResultDataAccessException.printStackTrace();
			message = "User You are trying to delete, Doesnot exists";
			httpStatus = HttpStatus.NOT_FOUND;
		}
		catch(Exception exception) {
			exception.printStackTrace();
			message = "Exception while deleting the record";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		erpsystemResponse.getErpSystemResponse().put("message", message);
		
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}

	@Transactional
	public ResponseEntity<ErpsystemResponse> updateUserRole(UserPOJO userPOJO) {
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		HttpStatus httpStatus = HttpStatus.OK;
		
		try {
			userRepository.updateRoleById(userPOJO.getUserId(), userPOJO.getRolesList());
		}catch(Exception exception) {
			httpStatus= HttpStatus.NOT_FOUND;
			erpsystemResponse.getErpSystemResponse().put("message", "User is not available in DB. Update Failed");
			exception.printStackTrace();
		}
		
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
	}
	
}
