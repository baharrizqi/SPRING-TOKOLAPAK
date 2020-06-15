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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
import com.cimb.tokolapak.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@PostMapping("/department/{departmentId}")
	public Employee addEmployee(@RequestBody Employee employee,@PathVariable int departmentId) {
		Department findDepartment = departmentRepo.findById(departmentId).get();
		
		if(findDepartment == null)
			throw new RuntimeException("Department not found");
		
		employee.setDepartment(findDepartment);
		
		return employeeRepo.save(employee);
	}
	
	@GetMapping
	public Iterable<Employee> getEmployees(){
		return employeeRepo.findAll();
	}
	
	@DeleteMapping("/address/{id}")
	public void deleteEmployeeAddressById(@PathVariable int id) {
		Optional<EmployeeAddress> employeeAddress = employeeAddressRepo.findById(id);
		
		if(employeeAddress.get() == null) {
			throw new RuntimeException("Employee Address not found");
		}
		employeeService.deleteEmployeeAddress(employeeAddress.get());
	}

	@PutMapping("/{employeeId}/address")
	public Employee addAddressToEmployee(@RequestBody EmployeeAddress employeeAddress,@PathVariable int employeeId){
		Employee findEmployee = employeeRepo.findById(employeeId).get();

		if(findEmployee == null)
			throw new RuntimeException("Employee not found");
		
		findEmployee.setEmployeeAddress(employeeAddress);
		return employeeRepo.save(findEmployee);
	}

	// @PutMapping
	// public Employee updateEmployeeAdress(@RequestBody Employee employee) {
	// 	return employeeRepo.save(employee);
	// }
	
	@PutMapping("{employeeId}/department/{departmentId}")
	public Employee addEmployeeToDepartment(@PathVariable int departmentId, @PathVariable int employeeId) {
		Employee findEmployee = employeeRepo.findById(employeeId).get();
		
		if(findEmployee == null)
			throw new RuntimeException("Employee not found");
		
		Department findDepartment = departmentRepo.findById(departmentId).get();
		
		if(findDepartment == null)
			throw new RuntimeException("Department not found");
		
		findEmployee.setDepartment(findDepartment);
		
		return employeeRepo.save(findEmployee);
		
//		return departmentRepo.findById(departmentId).map(department -> {
//			findEmployee.setDepartment(department);
//			return employeeRepo.save(findEmployee);
//		}).orElseThrow(() -> new RuntimeException("Department not found"));
	}
}
