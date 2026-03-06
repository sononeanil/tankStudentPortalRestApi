package com.anil.erp.util;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.anil.erp.common.ErpsystemResponse;

public class ZoomSignatureUtil {

    public static ResponseEntity<ErpsystemResponse> generateSignature(
            String sdkKey,
            String apiSecret,
            String meetingNumber,
            int role) {

        String message = "Web event Created successfully. Enjoy seamless teaching";
        HttpStatus httpStatus = HttpStatus.OK;
        ErpsystemResponse erpsystemResponse = new ErpsystemResponse();

        try {
        	System.out.println(sdkKey + "," + apiSecret + "," + meetingNumber + "," + role);
            long ts = (System.currentTimeMillis() - 30000);
            String msg = sdkKey + meetingNumber + ts + role;

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256"));
            byte[] hash = mac.doFinal(msg.getBytes());

            // ✅ Build signature string once
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            // ✅ Build the signature string
            String rawSignature = sdkKey + "." + meetingNumber + "." + ts + "." + role + "." + hashBase64;

            String signature = Base64.getEncoder().encodeToString(rawSignature.getBytes());
     

            // ✅ Put raw signature string (no double Base64)
            erpsystemResponse.getErpSystemResponse().put("signature", signature);
            System.out.println(signature);

        } catch (Exception e) {
            message = "Error generating Zoom signature";
            httpStatus = HttpStatus.BAD_REQUEST;
            e.printStackTrace();
        }

        erpsystemResponse.getErpSystemResponse().put("message", message);
        return new ResponseEntity<>(erpsystemResponse, httpStatus);
    }
}