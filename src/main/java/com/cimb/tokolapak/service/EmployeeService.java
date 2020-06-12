package com.cimb.tokolapak.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;

public interface EmployeeService {
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress);
}
