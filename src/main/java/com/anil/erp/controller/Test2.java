package com.anil.erp.controller;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test2 {
	
	public void getCharcount() {
		
		String s = "India";
		
//		Arrays.asList(s.toCharArray()).stream().collect(Collectors.toMap(Function.identity(), Collectors.counting()))
//		.entrySet(entry -> System.out.printl(entry.getKey() + "," + entry.getValue()));//Function.identity(),
		
	}
	
	public static void main(String[] args) {
		
		Test2 test2  = new Test2();
		test2.getCharcount();
		
	}

}
