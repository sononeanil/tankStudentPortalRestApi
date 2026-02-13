package com.anil.erp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.CustomerEntity;
import com.anil.erp.entity.LoginEntity;
import com.anil.erp.entity.UserEntity;
import com.anil.erp.repository.CustomerRepository;
import com.anil.erp.repository.LoginRepository;
import com.anil.erp.repository.UserRepository;
import com.anil.erp.util.JwtUtil;

@Service
public class LoginService {
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @Autowired
    private JwtUtil jwtUtil;

   
	
    public ResponseEntity<ErpsystemResponse> createLogin(LoginEntity loginEntity) {
        Optional<UserEntity> optinalUserEntity = userRepository.getByEmail(loginEntity.getUserId());
        ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
        HttpStatus httpStatus = HttpStatus.OK;
        UserEntity userEntity =  null;
        if (optinalUserEntity.isPresent()) {
             userEntity = optinalUserEntity.get();
            if (userEntity.getEmail().equals(loginEntity.getUserId()) &&
                userEntity.getPassword().equals(loginEntity.getPassword())) {

                erpsystemResponse.getErpSystemResponse().put("createLogin", "User got access to the system");
                /**
                 * Logic for generating role based jwt token
                 * from DB , system will get single string like USER, ADMIN, STUDENT
                 * convert this string into list of roles and pass it to generate token method
                 */
                
                List<String> lstRoles =  Arrays.stream(userEntity.getRole().split(","))
                .map(String::trim)
               
                .filter(s -> !s.isEmpty())
                .map(s -> "ROLE_" + s)
                .toList();
                
                erpsystemResponse.getErpSystemResponse().put("jwtToken", jwtUtil.generateToken(loginEntity.getUserId(), lstRoles));
                erpsystemResponse.getErpSystemResponse().put("loggedInUser", userEntity);

            } else {
                erpsystemResponse.getErpSystemResponse().put("message", "Invalid login id or password");
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
        } else {
            erpsystemResponse.getErpSystemResponse().put("message", "UserID not present. Please Sign up first");
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(erpsystemResponse, httpStatus);
    }

    
	public ResponseEntity<ErpsystemResponse> getLoginList() {
		List<LoginEntity> lstLogin = loginRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("loginList", lstLogin);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
	}
	

	public ResponseEntity<ErpsystemResponse> deleteLogin(long loginId) {
		loginRepository.deleteById(loginId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}
	
}
