package com.example.demo.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.EmployeeApplication.EmployeeApplication;
import com.EmployeeApplication.DAO.EmployeeDatabase;
import com.EmployeeApplication.Model.Employee;
import com.EmployeeApplication.Service.EmployeeService;

@SpringBootTest(classes =EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )

public class EmployeeServiceTest {
	

	private static final String name= "TestUser";
	private static final String desgination ="TestUser";
	private static final String company ="TestUser";
	
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

	
	
	private Employee getEmployee() {
		// TODO Auto-generated method stub
		Employee empreq = new Employee();
		
		empreq.setCompany(company);
		empreq.setDesignation(desgination);
		empreq.setName(name);
		
		return empreq;
	}
}
