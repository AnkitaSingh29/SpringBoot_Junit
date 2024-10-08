package com.EmployeeApplication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //lombok: generate getters setters
@NoArgsConstructor //Hibernate/JPA needs default constructor to create object 
@AllArgsConstructor

public class Employee {
	@GeneratedValue
	@Id
	Integer id;
	String name;
	String designation;
	String Company;
	
	
}
