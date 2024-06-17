package com.eldorado.El_Dorado.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder (String message, HttpStatus code, Object responseObject){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("code", code);
        response.put("data", responseObject);
        return new ResponseEntity<>(response, code);
    }
}
