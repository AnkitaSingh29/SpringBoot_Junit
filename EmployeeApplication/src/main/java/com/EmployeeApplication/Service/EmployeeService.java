package com.EmployeeApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.EmployeeApplication.DAO.EmployeeDatabase;
import com.EmployeeApplication.Model.Employee;

@Service
public class EmployeeService {
	@Autowired
	EmployeeDatabase dao;
	
	
	public List<Employee> fetchAllEmployee()
	{
		
		return dao.findAll();
	}
	
	

	public Employee findById(int id) {
		// TODO Auto-generated method stub
		Optional<Employee> emp = dao.findById(id);
		return emp.orElse(null);
	}
	
	public Employee addEmployee(Employee emp)
	{
		System.out.println("*Inside the EmployeeService_addEmployee()*"+emp.getId());
		return dao.save(emp);
		
	}

	public ResponseEntity<String> deleteEmployee(int id)
	{
		Employee delemp = findById(id);
		if(delemp!=null)
		{
			dao.delete(delemp);
			return new ResponseEntity<>("Employee deleted successfully",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Employee not found",HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Employee> getEmployeeByName(String empname) {
		// TODO Auto-generated method stub
		Employee findEmployeeByName = dao.findByName(empname);
		if(findEmployeeByName!=null)
		{
			return new ResponseEntity<>(findEmployeeByName,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
	}


}
