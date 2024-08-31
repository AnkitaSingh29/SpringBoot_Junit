package com.EmployeeApplication.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.EmployeeApplication.Model.Employee;

@Repository
public interface EmployeeDatabase extends JpaRepository<Employee, Integer> {
	
	Employee findByName(String EmpName);
	
	

}
