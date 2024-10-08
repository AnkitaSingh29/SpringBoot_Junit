package com.EmployeeApplication.PDFGenerator;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.EmployeeApplication.DAO.EmployeeDatabase;
import com.EmployeeApplication.Model.Employee;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PDFGenerator {

	
	
	@Value("${PdfName}")
	private String PdfName;
	
	@Value("${FileNameDateFormat}")
	private String reportFileNameDateFormat;
	
	@Value("${localDateFormat}")
	private String localDateFormat;

	@Value("${table_noOfColumns}")
	private int noOfColumns;
	
	@Value("${table.columnNames}")
	private List<String> columnNames;
	
	@Autowired
	EmployeeDatabase dao;
	
	
	public void  generatePDF(HttpServletResponse response)
	{
		Document document = new Document();
		
		try 
		{
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			createTable(document,noOfColumns);
			document.close();
			System.out.println("------------------Your PDF Report is ready!-------------------------");
			
		}
		catch(Exception exception )
		{
			exception.printStackTrace();
		}
		
	}
	

	private void createTable(Document document, int columns) throws DocumentException
	{
		
		PdfPTable table = new PdfPTable(columns);
		
		
		for(int i=0; i<columns; i++) 
		{
			PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
			table.addCell(cell);
		}
		table.setHeaderRows(1);
		populateDBData(table);
		document.add(table);
		
	}
	private void populateDBData(PdfPTable table)
	{
		
		List<Employee> employeeList = dao.findAll();
		for(Employee emp: employeeList)
		{
			table.addCell(emp.getId().toString());
			table.addCell(emp.getName());
			table.addCell(emp.getDesignation());
			table.addCell(emp.getCompany());
		}
		
	}
	
}
