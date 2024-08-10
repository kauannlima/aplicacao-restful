package br.com.klima.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.klima.converters.NumberConverter;
import br.com.klima.exceptions.ResourceNotFoundException;
import br.com.klima.math.SimpleMath;

@RestController
public class MathController {
	
	private static final AtomicLong counter = new AtomicLong();
	
	private SimpleMath math = new SimpleMath();
	
	@RequestMapping(value = "/sum/{numberOne}/{numbertwo}", method=RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numbertwo") String numbertwo
			) throws Exception{
		
		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numbertwo)) {
			 throw new ResourceNotFoundException("Please set a numeric value!");
		} 
		return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numbertwo));
	}
	
	@RequestMapping(value = "/subtraction/{numberOne}/{numbertwo}", method=RequestMethod.GET)
	public Double subtraction(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numbertwo") String numbertwo
			) throws Exception{
		
		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numbertwo)) {
			 throw new ResourceNotFoundException("Please set a numeric value!");
		} 
		return math.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numbertwo));
	}
	
	@RequestMapping(value = "/multiplication/{numberOne}/{numbertwo}", method=RequestMethod.GET)
	public Double multiplication(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numbertwo") String numbertwo
			) throws Exception{
		
		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numbertwo)) {
			 throw new ResourceNotFoundException("Please set a numeric value!");
		} 
		return math.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numbertwo));
	}
	
	@RequestMapping(value = "/division/{numberOne}/{numbertwo}", method=RequestMethod.GET)
	public Double division(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numbertwo") String numbertwo
			) throws Exception{
		
		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numbertwo)) {
			 throw new ResourceNotFoundException("Please set a numeric value!");
		} 
		return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numbertwo));
	}
	
	@RequestMapping(value = "/mean/{numberOne}/{numbertwo}", method=RequestMethod.GET)
	public Double mean(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numbertwo") String numbertwo
			) throws Exception{
		
		if(!NumberConverter.isNumeric(numberOne)|| !NumberConverter.isNumeric(numbertwo)) {
			 throw new ResourceNotFoundException("Please set a numeric value!");
		} 
		return math.mean(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numbertwo));
	}
	
	@RequestMapping(value = "/squareroot/{numberOne}", method=RequestMethod.GET)
	public Double squareRoot(
			@PathVariable(value = "numberOne") String numberOne
			) throws Exception{
		
		if(!NumberConverter.isNumeric(numberOne)) {
			 throw new ResourceNotFoundException("Please set a numeric value!");
		} 
				
		return math.squareRoot(NumberConverter.convertToDouble(numberOne));
	}

	

}
