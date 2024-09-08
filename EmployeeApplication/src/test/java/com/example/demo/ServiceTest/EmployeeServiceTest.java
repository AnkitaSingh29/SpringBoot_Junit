package com.example.demo.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.EmployeeApplication.EmployeeApplication;
import com.EmployeeApplication.DAO.EmployeeDatabase;
import com.EmployeeApplication.Model.Employee;
import com.EmployeeApplication.Service.EmployeeService;

@SpringBootTest(classes =EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )

public class EmployeeServiceTest {
	

	private static final String name= "TestUser";
	private static final String desgination ="TestUser";
	private static final String company ="TestUser";
	private static final int ID = 1;
	
	@Mock
	private EmployeeDatabase emprepository;
	
	@InjectMocks
	private EmployeeService empService;
	
	@Test
	public void should_save_emp()
	{
		
		System.out.println("**Entering the EmployeeServiceTest.should_save_emp**");
		Employee emp = getEmployee();
		emp.setId(0);
		when(emprepository.save(emp)).thenReturn(emp);
		
		assertEquals(emp, empService.addEmployee(emp));
	}
	

	
	@Test
	public void should_fetch_emp_by_id()
	{
		
		System.out.println("**Entering the EmployeeServiceTest.should_fetch_emp_by_id**");
		Employee emp = getEmployee();
		
		when(emprepository.findById(ID)).thenReturn(Optional.of(emp));
		assertEquals(emp, empService.findById(ID));
	}

	

	@Test
	public void should_return_null_fetch_emp_by_id()
	{
		
		System.out.println("**Entering the EmployeeServiceTest.should_return_null_fetch_emp_by_id**");
			
		when(emprepository.findById(ID)).thenReturn(Optional.empty());
		assertNull(empService.findById(ID));
	}
	
	
	
	
	@Test
	public void should_fetch_emp_by_name()
	{
		
		System.out.println("**Entering the EmployeeServiceTest.should_fetch_emp_by_id**");
		Employee emp = getEmployee();
		when(emprepository.findByName(name)).thenReturn(emp);
		assertEquals(empService.getEmployeeByName(name).getStatusCode(), HttpStatus.OK);
		assertEquals(emp, empService.getEmployeeByName(name).getBody());
	}
	

	@Test
	public void should_return_null_emp_by_name()
	{
		
		System.out.println("**Entering the EmployeeServiceTest.should_fetch_emp_by_id**");
		Employee emp = getEmployee();
		when(emprepository.findByName(name)).thenReturn(null);
		assertEquals(empService.getEmployeeByName(name).getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(null, empService.getEmployeeByName(name).getBody());
	}
	
	

	@Test
	public void should_find_all_emp()
	{
		
		System.out.println("**Entering the EmployeeServiceTest.should_find_all_emp**");
		List<Employee> empList = Collections.singletonList(getEmployee());
		when(emprepository.findAll()).thenReturn(empList);
		assertEquals(empService.fetchAllEmployee(), empList);
	}
	

	
	

	@Test
	public void should_delete_emp_by_id()
	{
		
		Employee emp = getEmployee();
		emp.setId(0);
		when(emprepository.findById(emp.getId())).thenReturn(Optional.of(emp));
		 doNothing().when(emprepository).deleteById(emp.getId());
		assertEquals(empService.deleteEmployee(0).getBody(),"Employee deleted successfully");
	}
	

	@Test
	public void should_return_unsuccessful_delete_emp_by_id()
	{
		
		Employee emp = getEmployee();
		emp.setId(0);
		when(emprepository.findById(ID)).thenReturn(Optional.empty());
		 doNothing().when(emprepository).deleteById(emp.getId());
		assertEquals(empService.deleteEmployee(0).getBody(),"Employee not found");
		assertEquals(empService.deleteEmployee(ID).getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	

	
	private Employee getEmployee() {
		// TODO Auto-generated method stub
		Employee empreq = new Employee();
		
		empreq.setCompany(company);
		empreq.setDesignation(desgination);
		empreq.setName(name);

		
		return empreq;
	}
}
