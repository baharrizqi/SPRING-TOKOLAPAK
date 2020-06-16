package com.cimb.tokolapak.dao;

import com.cimb.tokolapak.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project,Integer> {
    
}