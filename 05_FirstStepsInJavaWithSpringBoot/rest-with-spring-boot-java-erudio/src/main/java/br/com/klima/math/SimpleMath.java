package br.com.klima.math;


public class SimpleMath {
	

	public Double sum(Double numberOne, Double numbertwo) {
		return numberOne + numbertwo;
	}
	
	public Double subtraction(Double numberOne, Double numbertwo) {
		return numberOne - numbertwo;
	}
	
	public Double multiplication(Double numberOne, Double numbertwo) {
		return numberOne * numbertwo;
	}
	
	public Double division(Double numberOne, Double numbertwo) {
		return numberOne / numbertwo;
	}
	
	public Double mean(Double numberOne, Double numbertwo) {
		return (numberOne + numbertwo)/2;
	}
	
	public Double squareRoot(Double numberOne) {
		return Math.sqrt(numberOne);
	}
	
}
