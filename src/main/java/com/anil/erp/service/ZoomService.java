package com.anil.erp.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anil.erp.common.ErpsystemResponse;
import com.anil.erp.entity.ZoomEntity;
import com.anil.erp.pojo.ZoomPOJO;
import com.anil.erp.repository.ZoomRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Service
public class ZoomService {

    private static final String SDK_KEY = "H_e5qgo0Q_Pnz3Yt5pMUQ";
    private static final String SDK_SECRET = "rUUQYG4bs3VtmCtnBawzfgouf6ex8nqn";
    
    @Autowired
    ZoomRepository zoomRepository;

    public  ResponseEntity<ErpsystemResponse> generateSignature(String meetingNumber1, int role) {
    	
    	Map zoomMeeting = createMeeting();
    	String meetingNumber = String.valueOf(zoomMeeting.get("id")) ;
        long iat = System.currentTimeMillis() / 1000;
        long exp = iat + 60 * 60 * 2;

        Map<String, Object> payload = new HashMap<>();
        payload.put("sdkKey", SDK_KEY);
        payload.put("mn", meetingNumber);
        payload.put("role", role);
        payload.put("iat", iat);
        payload.put("exp", exp);
        payload.put("appKey", SDK_KEY);
        payload.put("tokenExp", exp);

        SecretKey key = Keys.hmacShaKeyFor(SDK_SECRET.getBytes());
        ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
        erpsystemResponse.getErpSystemResponse().put("signature", Jwts.builder()
                .setClaims(payload)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact());
        erpsystemResponse.getErpSystemResponse().put("meetingPassword", String.valueOf(zoomMeeting.get("password")));
        erpsystemResponse.getErpSystemResponse().put("meetingNumber", meetingNumber);

        return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
    }
    
    private Map createScheduledMeeting(ZoomPOJO zoomEntity) {

	  	String url = "https://api.zoom.us/v2/users/me/meetings";

	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(generateAccessToken());
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    Map<String, Object> body = new HashMap<>();
	    body.put("topic", zoomEntity.getMeetingTopic());
	    body.put("type", 2);
	    body.put("duration", zoomEntity.getDuration());
	    body.put("timezone", "Asia/Kolkata");
	    
	    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

	    RestTemplate restTemplate = new RestTemplate();

	    ResponseEntity<Map> response =
	            restTemplate.postForEntity(url, request, Map.class);

	    Map meetingDetails = response.getBody();
	    return meetingDetails;

    }
    
    /**
     * This method creates instant meeting with predefined values ONLY
     * If teacher want to start the instant meeting, then he can call this function
     * @return
     */
    private Map createMeeting() {
    	  	String url = "https://api.zoom.us/v2/users/me/meetings";

    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setBearerAuth(generateAccessToken());
    	    headers.setContentType(MediaType.APPLICATION_JSON);

    	    Map<String, Object> body = new HashMap<>();
    	    body.put("topic", "React Embedded Meeting");
    	    body.put("type", 2);
    	    body.put("duration", 30);
    	    body.put("timezone", "Asia/Kolkata");
    	    
    	    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    	    RestTemplate restTemplate = new RestTemplate();

    	    ResponseEntity<Map> response =
    	            restTemplate.postForEntity(url, request, Map.class);

    	    Map data = response.getBody();
    	    return data;
    }
    
    private String generateAccessToken() {

        String url = "https://zoom.us/oauth/token?grant_type=account_credentials&account_id=0z2iIoYWTKKoXjrSbCcdzg";

        HttpHeaders headers = new HttpHeaders();

        String auth = Base64.getEncoder()
                .encodeToString(("F7FNelKFTgeHUo9alLQ" + ":" + "bJjvO7o4VbhmnDdebLuv1BTLPNIsc37t").getBytes());

        headers.set("Authorization", "Basic " + auth);

        HttpEntity<String> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        Map body = response.getBody();

        return (String) body.get("access_token");
    }
    
    public ResponseEntity<ErpsystemResponse> getZoomList() {
		List<ZoomEntity> lstZoom = zoomRepository.findAll();
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		erpsystemResponse.getErpSystemResponse().put("zoomList", lstZoom);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, HttpStatus.OK);
		
	}
	
	public ResponseEntity<ErpsystemResponse> createZoom(ZoomPOJO zoomPojo) {
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		String message = "Zoom Meeting Successfully got created";
		HttpStatus httpStatus = HttpStatus.CREATED;
		try {
			Map hmZoomMeetingDetails = createScheduledMeeting(zoomPojo);
			ZoomEntity zoomEntity = new ZoomEntity();
			zoomEntity.setMeetingId(hmZoomMeetingDetails.get("id").toString());
			zoomEntity.setDuration(hmZoomMeetingDetails.get("duration").toString());
			zoomEntity.setMeetingTopic((String)hmZoomMeetingDetails.get("topic"));
			zoomEntity.setStartTime((String)hmZoomMeetingDetails.get("start_time"));
			zoomEntity.setStartUrl((String)hmZoomMeetingDetails.get("start_url"));
			zoomEntity.setJoinUrl((String)hmZoomMeetingDetails.get("join_url"));
			zoomEntity.setPassword((String)hmZoomMeetingDetails.get("password"));
			zoomEntity.setOrganizerEmail((String)hmZoomMeetingDetails.get("host_email"));
			zoomRepository.save(zoomEntity);
			erpsystemResponse.getErpSystemResponse().put("createZoom", "Zoom got Registred in the system");
		}catch(Exception exception) {
			exception.printStackTrace();
			message = "Unable to create zoom Meeting. Please connect with your Admin";
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		erpsystemResponse.getErpSystemResponse().put("message", message);
		return new ResponseEntity<ErpsystemResponse>(erpsystemResponse, httpStatus);
		
	}

	public ResponseEntity<ErpsystemResponse> deleteZoom(long zoomId) {
		zoomRepository.deleteById(zoomId);
		ErpsystemResponse erpsystemResponse = new ErpsystemResponse();
		return new ResponseEntity<ErpsystemResponse>(HttpStatus.GONE);
	}
    
}