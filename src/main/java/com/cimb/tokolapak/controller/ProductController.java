package com.cimb.tokolapak.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.ProductRepo;
import com.cimb.tokolapak.entity.Product;
import com.cimb.tokolapak.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {

	// Controller -> service -> Dao/Repo -> DB

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public Iterable<Product> getProducts() {
		return productService.getProducts();
	}

	@GetMapping("/products/{id}")
	public Optional<Product> getProductById(@PathVariable int id) {
		return productService.getProductById(id);
	}

	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}

	@DeleteMapping("/products/{id}")
	public void deleteProductById(@PathVariable int id) {
//		Optional<Product> product = this.productRepo.findById(id);
//		
//		if(product == null) {
//			throw new RuntimeException("Product with id "+id + " does not exist");
//		}
		this.productService.deleteProductById(id);
	}

	@PutMapping("/products")
	public Product updateProduct(@RequestBody Product product) {
		return productService.updateProduct(product);
	}

	@GetMapping("/productName/{productName}")
	public Product getProductByName(@PathVariable String productName) {
		return productRepo.findByProductName(productName);
	}
}
