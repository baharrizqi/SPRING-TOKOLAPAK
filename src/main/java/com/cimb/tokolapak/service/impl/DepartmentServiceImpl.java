package com.cimb.tokolapak.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepo departmentRepo;
	
	@Override
	@Transactional
	public Iterable<Department> getAllDepartments() {
		return departmentRepo.findAll();
	}

	@Override
	@Transactional
	public Department addDepartments(Department department) {
		department.setId(0);
		return departmentRepo.save(department);
	}
	
}
