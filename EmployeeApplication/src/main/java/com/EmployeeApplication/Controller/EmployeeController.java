package com.EmployeeApplication.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.EmployeeApplication.Model.Employee;
import com.EmployeeApplication.PDFGenerator.PDFGenerator;
import com.EmployeeApplication.Service.EmployeeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService service;
	
	@Autowired
	PDFGenerator pdf;
	
	
	@GetMapping("")
	public ResponseEntity<List<Employee>> fetchAllEmployee()
	{
		return ResponseEntity.ok(service.fetchAllEmployee());
		
	}
	
	
	@GetMapping("/{id}")
	public Employee findByID(@PathVariable int id)
	{
		return service.findById(id);
		
	}
	
	@PostMapping("")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee emp)
	{
		System.out.println("*Inside the EmployeeController_addEmployee()*"+emp.getId());
		Employee empadded= service.addEmployee(emp);
		return ResponseEntity.ok(empadded);
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<String> deleteEmployee(@PathVariable int id)
	{
		return service.deleteEmployee(id);
		
	}


	
	@GetMapping("/getEmployeeByName")
	public ResponseEntity<Employee> getEmployeeByNameRequestParam(@RequestParam String Empname)
	{
		return service.getEmployeeByName(Empname);
	}
	
	@GetMapping("/getEmployeeByName/{Empname}")
	public ResponseEntity<Employee> getEmployeeByNamePathParam(@PathVariable String Empname)
	{
		return service.getEmployeeByName(Empname);
	}
	
	@GetMapping("/generatePDF")
	public void  generatePDF(HttpServletResponse httpServletResponse)
	{
		httpServletResponse.setContentType("application/pdf");
		pdf.generatePDF(httpServletResponse);
		 
	}
	

	
	
}
