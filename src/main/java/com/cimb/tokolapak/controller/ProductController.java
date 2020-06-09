package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.ProductRepo;
import com.cimb.tokolapak.entity.Product;


@RestController
public class ProductController {
	
	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping("/products")
	public Iterable<Product> getProducts() {
		return productRepo.findAll();
	}
	@GetMapping("/products/{id}")
	public Optional<Product> getProductById(@PathVariable() int id) {
		return productRepo.findById(id);
	}
}
