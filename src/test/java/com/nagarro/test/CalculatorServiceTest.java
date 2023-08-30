package com.nagarro.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nagarro.service.CalculatorService;

public class CalculatorServiceTest {

	
	@Test
	public void additionTest() {
		CalculatorService service = new CalculatorService();
		String expectedResult  = "11.0";
		String actualResult = service.calculate(5.0, 6.0, "+");
		
		assertEquals(expectedResult,actualResult);
	}
	@Test
	public void substractionTest() {
		CalculatorService service = new CalculatorService();
		String expectedResult  = "5.0";
		String actualResult = service.calculate(11.0, 6.0, "-");
		
		assertEquals(expectedResult,actualResult);
	}
	@Test
	public void multiplicationTest() {
		CalculatorService service = new CalculatorService();
		String expectedResult  = "0.0";
		String actualResult = service.calculate(100.0, 0.0, "*");
		
		assertEquals(expectedResult,actualResult);
	}
	@Test
	public void divisionTest() {
		CalculatorService service = new CalculatorService();
		String expectedResult  = "1.0";
		String actualResult = service.calculate(5.0, 5.0, "/");
		
		assertEquals(expectedResult,actualResult);
	}
	@Test
	public void divisionByZeroTest() {
		CalculatorService service = new CalculatorService();
		String expectedResult  = "Can't divide by zero";
		String actualResult = service.calculate(5.0, 0.0, "/");
		
		assertEquals(expectedResult,actualResult);
	}
	
	
	
}
