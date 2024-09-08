package com.example.demo.DAOTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.EmployeeApplication.EmployeeApplication;
import com.EmployeeApplication.DAO.EmployeeDatabase;
import com.EmployeeApplication.Model.Employee;

@DataJpaTest
@ContextConfiguration(classes = EmployeeApplication.class)
public class EmployeeRepositoryTest {
	
	
		@Autowired
		private ApplicationContext container;
	
	    private Employee testUser;

		@Autowired
		private EmployeeDatabase empRepository;
		
	    @Autowired
	    private TestEntityManager testEntityManager; //used to communicate with in memory database
		 
	
	@BeforeEach
	public void setUp() {
	    // Initialize test data before each test method
		System.out.println("Inside the Repository test class");
		System.out.println("Beans initialized in container="+Arrays.toString(container.getBeanDefinitionNames()));
	    testUser = new Employee();
	    testUser.setCompany("TestCompany");
	    testUser.setDesignation("TestDesignation");
	    testUser.setName("TestEmployee");
	    empRepository.save(testUser);
	}
	
		    
		    
	   @Test
	    public void should_save_user() {
		   System.out.println("**Inside the EmployeeRepositoryTest class should_save_user() ** ");
		   
		   Employee fetchedEmp = testEntityManager.find(Employee.class, testUser.getId());
		   
		   assertEquals(fetchedEmp, testUser);
	   
	   
	   }
	   
	   @Test
	   public void should_find_user_by_id()
	   {
		   Optional<Employee> fetched = empRepository.findById(testUser.getId());
		   //fetched is of type optional<Employee> so we are doing fetched.get() which would return employee object if present
		   assertEquals(fetched.get(), testUser);
	   }
	   
//	   @Test
//	   public void should_find_user_by_id()
//	   {
//		   Optional<Employee> fetched = empRepository.findById(testUser.getId());
//		   //fetched is of type optional<Employee> so we are doing fetched.get() which would return employee object if present
//		   assertEquals(fetched.get(), testUser);
//	   }
	   
	   @Test
	   public void should_find_all_users()
	   {
		   List<Employee> empList = empRepository.findAll();
		   assertThat(empList).isNotNull();
		   assertThat(empList).contains(testUser);
		   
	   }
	 
}
