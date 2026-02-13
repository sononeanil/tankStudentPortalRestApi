package com.anil.erp.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Test {
	
	public void get3Higested() {
		Map<String, Integer> map = new HashMap<>();
		map.put("Java", 35);
		map.put("JPA", 45);
		map.put("Angular", 20);
		map.put("React", 41);
		map.put("DotNet", 42);
		
//		map.keySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue());
		map.entrySet()
		            .stream()
		            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
		            .limit(3);
//		map.keySet().stream().collect(Collectors.)

	}
	
	public static void main(String[] args) {
		System.out.println("*****start**");
	}

}
