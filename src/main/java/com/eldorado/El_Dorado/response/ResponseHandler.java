package com.eldorado.El_Dorado.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

//    private String code;
//    private String message;
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

    public static ResponseEntity<Object> responseBuilder (String message, HttpStatus code, Object responseObject){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("code", code.value());
        response.put("data", responseObject);
        return new ResponseEntity<>(response, code);
    }

//    public static ResponseEntity<Object> failureResponseBuilder(String message, HttpStatus code){
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", message);
//        response.put("code", code);
//        return new ResponseEntity<>(response, code);
//    }
}
