package com.example.demo.ControllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.EmployeeApplication.EmployeeApplication;
import com.EmployeeApplication.Controller.EmployeeController;
import com.EmployeeApplication.Model.Employee;

import com.example.demo.helper.HTTPHelper;



@SpringBootTest(classes =EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )

public class EmployeeControllerIntegrationTest {

	private static final int id =2;
	private static final String name= "TestUser";
	private static final String desgination ="TestUser";
	private static final String company ="TestUser";
	
	
	private static final String employeeURL= "http://localhost:%s/employee";
	//private static final String employeeIDURL= "\"http://localhost:%s/employee/%s";
	
	@Autowired
	private ApplicationContext container;
	
	
	@Autowired
	private TestRestTemplate testresttemplate;
	
	@LocalServerPort//-->assigns the port which is assigned during the server runtime
	private int port;
	
	
	@Test
	public void setup()
	{
		System.out.println("Inside the setup method of ControllerTest class");
		System.out.println("Beans in container: "+Arrays.toString(container.getBeanDefinitionNames()));
		assertNotNull(container);
		assertThat(container.getBean(EmployeeController.class)).isNotNull();
	}
	
	
	@Test
	public void test_1_should_save_employee()
	{
		//form the request which is similar to the request sent when u hit the url
		String url = String.format(employeeURL,port); // will add the port to the url
		Employee empreq = getEmployeeRequest();
		empreq.setId(id);
		HttpEntity<Employee> httprequest = HTTPHelper.getHttpEntity(empreq);
		System.out.println("**Inside the test1_should_save_employee**"+httprequest);
		System.out.println(url);
		//sends an HTTP POST request to the specified url using testRestTemplate
		ResponseEntity<Employee> response = testresttemplate.exchange(url,HttpMethod.POST, httprequest,Employee.class);
		System.out.println(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response);
		
		assertEquals(response.getBody().getCompany(), company);
		assertEquals(response.getBody().getName(),name);
		
	}


	private Employee getEmployeeRequest() {
		// TODO Auto-generated method stub
		Employee empreq = new Employee();
		empreq.setCompany(company);
		empreq.setDesignation(desgination);
		empreq.setName(name);
		
		return empreq;
	}
}
