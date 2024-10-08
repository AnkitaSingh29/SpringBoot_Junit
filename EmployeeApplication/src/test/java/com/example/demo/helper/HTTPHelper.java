package com.example.demo.helper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class HTTPHelper {
	public static HttpEntity<String> getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
        return new HttpEntity<>(httpHeaders);
    }
	 public static <T> HttpEntity<T> getHttpEntity(T dto) {
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
	        return new HttpEntity<>(dto, httpHeaders);
	    }

}
