package com.example.demo.ControllerTest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;


import com.EmployeeApplication.EmployeeApplication;
import com.EmployeeApplication.Controller.EmployeeController;
import com.EmployeeApplication.Model.Employee;

import com.example.demo.helper.HTTPHelper;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;

@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest(classes =EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )


public class EmployeeControllerIntegrationTest {

	private static final int id =1;
	private static final String name= "TestUser";
	private static final String desgination ="TestUser";
	private static final String company ="TestUser";
	
	
	private static final String employeeURL= "http://localhost:%s/employee";
	private static final String employeeIDURL= "http://localhost:%s/employee/%s";
	private static final String employeeNameURL= "http://localhost:%s/employee/getEmployeeByName/%s";
	private static final String employeeNameRequestParamURL= "http://localhost:%s/employee/getEmployeeByName?Empname=%s";
	private static final String employeePDF= "http://localhost:%s/employee/generatePDF";
	
	@Autowired
	private ApplicationContext container;
	
	
	@Autowired
	private TestRestTemplate testresttemplate;
	
	@LocalServerPort//-->assigns the port which is assigned during the server runtime
	private int port;
	
	
	@Test
	public void setup()
	{
		
		assertNotNull(container);
		assertThat(container.getBean(EmployeeController.class)).isNotNull();
		
	}
	
	
	@Test
	public void test_1_should_save_employee()
	{
		//form the request which is similar to the request sent when u hit the url
		String url = String.format(employeeURL,port); // will add the port to the url
		Employee empreq = getEmployee();
		
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
	
	
	@Test
	public void test_2_should_find_employee_by_id()
	{
		Employee testUser = getEmployee();
		testUser.setId(id);
		String url = String.format(employeeIDURL,port,testUser.getId());
		HttpEntity<String> httprequest = HTTPHelper.getHttpEntity();
		ResponseEntity<Employee> response = testresttemplate.exchange(url, HttpMethod.GET, httprequest,Employee.class);
		assertEquals(response.getStatusCode(),HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertEquals(response.getBody().getName(), testUser.getName());
		assertEquals(response.getBody().getCompany(),testUser.getCompany());
		assertEquals(response.getBody().getDesignation(),testUser.getDesignation());
		
}
	
		
	@Test
	public void test_3_should_fetch_all_employee()
	{
		Employee testUser = getEmployee();
		testUser.setId(id);
		String url = String.format(employeeURL,port);
		HttpEntity<String> httprequest = HTTPHelper.getHttpEntity();
		ResponseEntity<List<Employee>> response = testresttemplate.exchange(url, HttpMethod.GET, httprequest, new ParameterizedTypeReference<List<Employee>>() {});
		List<Employee> empList = response.getBody();
		assertThat(empList).contains(testUser);
		
		
	}
	
	
	@Test
	public void test_4_should_find_employee_by_name()
	{
		Employee testUser = getEmployee();
		
		String url = String.format(employeeNameURL,port,testUser.getName());
		HttpEntity<String> httprequest = HTTPHelper.getHttpEntity();
		System.out.println(url);
		ResponseEntity<Employee> response = testresttemplate.exchange(url, HttpMethod.GET, httprequest,Employee.class);
		assertEquals(response.getStatusCode(),HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertEquals(response.getBody().getName(), testUser.getName());
		assertEquals(response.getBody().getCompany(),testUser.getCompany());
		assertEquals(response.getBody().getDesignation(),testUser.getDesignation());
		
		
	}

	@Test
	public void test_5_should_find_employee_by_name()
	{
		Employee testUser = getEmployee();
		
		String url = String.format(employeeNameRequestParamURL,port,testUser.getName());
		HttpEntity<String> httprequest = HTTPHelper.getHttpEntity();
		System.out.println(url);
		ResponseEntity<Employee> response = testresttemplate.exchange(url, HttpMethod.GET, httprequest,Employee.class);
		assertEquals(response.getStatusCode(),HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertEquals(response.getBody().getName(), testUser.getName());
		assertEquals(response.getBody().getCompany(),testUser.getCompany());
		assertEquals(response.getBody().getDesignation(),testUser.getDesignation());
		
		
	}
	
	@Test
	public void test_6_should_generate_PDF()
	{
		
		
		String url = String.format(employeePDF,port);
		HttpEntity<String> httprequest = HTTPHelper.getHttpEntity();
		System.out.println(url);
		ResponseEntity<byte[]> response = testresttemplate.exchange(url, HttpMethod.GET, httprequest,byte[].class);
		assertEquals(response.getStatusCode(),HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getHeaders().getContentType().toString(),"application/pdf");
		
		
	}
	
	@Test
	public void test_7_should_delete_by_id()
	{
		Employee testUser = getEmployee();
		testUser.setId(id);
		String url = String.format(employeeIDURL,port,id);
		System.out.println(url);
		HttpEntity<String> httprequest = HTTPHelper.getHttpEntity();
		ResponseEntity<String> response = testresttemplate.exchange(url, HttpMethod.DELETE, httprequest, String.class);
		assertEquals(response.getStatusCode(),HttpStatus.OK);
		assertEquals(response.getBody(),"Employee deleted successfully");
	
		
		
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
