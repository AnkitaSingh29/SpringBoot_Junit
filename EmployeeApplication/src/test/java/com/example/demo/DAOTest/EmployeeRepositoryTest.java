package com.example.demo.DAOTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;





import com.EmployeeApplication.DAO.EmployeeDatabase;
import com.EmployeeApplication.Model.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {
	
	
	
	    private static final String name = "NAME";
	    private static final String designation = "UPDATED_NAME";
	    private static final String company = "UPDATED_Company";

		@Autowired
		private EmployeeDatabase empRepository;
		
	    @Autowired
	    private TestEntityManager testEntityManager;
		
	   @Test
	    public void should_save_user() {
		   System.out.println("*Inside the EmployeeRepositoryTest_should_save_user()*");
	        //Given
	        Employee emp = getEmployee();
	        //When
	        emp = empRepository.save(emp);
	        //Then
	        Employee actual = testEntityManager.find(Employee.class, emp.getId());
	        assertEquals(actual, emp);
	    }
	   
	   
	   private Employee getEmployee() {
	        Employee emp = new Employee();
	        emp.setCompany(company);
	        emp.setDesignation(designation);
	        emp.setName(name);
	        System.out.println("************"+emp);
	        return emp;
	    }
	
}
