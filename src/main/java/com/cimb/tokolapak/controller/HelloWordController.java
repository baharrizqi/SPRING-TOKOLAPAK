package com.cimb.tokolapak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class HelloWordController {
	@GetMapping("/hello")
	public String helloWorld() {
		return "Hellooo";
	}
	
	@GetMapping("/hello/{name}")
	public String helloName(@PathVariable() String name) {
		return "Hello " + name;
	}
	@GetMapping("/angka/{nomor}")
	public int angka(@PathVariable() int nomor) {
		return nomor;
	}
	
}
