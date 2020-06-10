package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Optional<Product> getProductById(@PathVariable int id) {
		return productRepo.findById(id);
	}
	@GetMapping("/productName/{productName}")
	public Product getProductByName(@PathVariable String productName) {
		return productRepo.findByProductName(productName);
	}
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		product.setId(0);
		return productRepo.save(product);
	}
	@DeleteMapping("/products/{id}")
	public void deleteProductById(@PathVariable int id) {
//		Optional<Product> product = this.productRepo.findById(id);
//		
//		if(product == null) {
//			throw new RuntimeException("Product with id "+id + " does not exist");
//		}
		this.productRepo.deleteById(id);
	}
	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product product) {
		Optional<Product> findProduct = productRepo.findById(product.getId());
		
		if(findProduct.toString() == "Optional.empty") {
			throw new RuntimeException("Product with id "+ product.getId()+ " does not exist");
		}
		return productRepo.save(product);	
	}
}
