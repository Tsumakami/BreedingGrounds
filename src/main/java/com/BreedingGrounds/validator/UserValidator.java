package com.BreedingGrounds.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class UserValidator {
	private static final String REGEX_VALID_EMAIL = "^(.+)@(.+)$";
	
	public static boolean testEmail(String email) {
		Pattern pattern = Pattern.compile(REGEX_VALID_EMAIL);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
	
	public static boolean testCpf(String cpf, boolean start) {
		if (cpf.matches("\\d{11}")) {
			boolean exception = IntStream.range(0, 9).boxed().filter(num -> (((11111111111L*num) + "").equals(cpf))).findFirst().orElse(-1) != -1;            
			int calc = 0, weight = start ? 10 : 11;
			
			//somando os digitos por um peso decrescente
			for (int i = 0; i < (start ? 9 : 10); i++) {
			    calc += Integer.parseInt(cpf.charAt(i) + "") * weight--;
			}
			
			//calculando o resto
			weight = calc * 10 % 11 == 10 ? 0 : calc * 10 % 11;
			
			//se inicio = true a validação ocorre no index 9 do array, se não no index 11
			return exception || !(weight + "").equals(cpf.charAt(start ? 9 : 10) + "") ? false : start ? testCpf(cpf, false) : true;
		} 
		
		return false;
	}

}
