package com.cadcon.cloud.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.cadcon.cloud.exception.InvalidInputException;
import com.cadcon.cloud.models.Address;
import com.cadcon.cloud.models.Email;
import com.cadcon.cloud.models.Phone;
import com.cadcon.cloud.models.StudentProfile;

@Service
public class ValidateUser {
	
    Pattern phoneNumberPattern = Pattern.compile("^\\d{10}$");
    Pattern emailIdPatter = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

	
	public void validateUserForSignup(StudentProfile student) throws Exception{
		validateStudent(student);
		validateName(student.getFirstName());
		validateAddress(student.getPrimaryAddress());
		validatePhoneNumbers(student.getPhoneNumbers());
		if(student.getPrimaryEmail() != null)
			validateEmailAddress(student.getPrimaryEmail());
	}
	
	private void validateStudent(StudentProfile student) throws Exception{
		if(student == null) {
			throw new InvalidInputException("Student object can't be empty");
		}
	}
	
	private void validateName(String firstName) throws Exception {
		if(firstName == null || firstName.isEmpty()) {
			throw new InvalidInputException("First Name of the student can't be empty");
		}
	}
	
	private void validateAddress(Address address) throws Exception {
		if(address == null) {
			throw new InvalidInputException("Address of the student can't be empty");
		}
		if(address.getState() == null || address.getState().isEmpty() ) {
			throw new InvalidInputException("State of the student can't be empty");
		}
		
		if(address.getPinCode() == null || address.getPinCode().isEmpty()) {
			throw new InvalidInputException("Pin Code is mandatory");
		}
	}
	
	private void validatePhoneNumbers(List<Phone> phoneNumberList) throws Exception{
		if(phoneNumberList.isEmpty() || phoneNumberList.size() == 0) {
			throw new InvalidInputException("Phone number is mandatory");
		}
		// Always considering first element in the phone as the primary phone number.
		// Validations are done only against the same.
		String phoneNumber = phoneNumberList.get(0).getNumber();
        Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
        if(!matcher.matches()) {
        	throw new InvalidInputException("Invalid Phone number provided. Phone number provided : " + phoneNumber);
        }
	}
	
	private void validateEmailAddress(List<Email> emailAddressList) throws Exception{
		for(Email email : emailAddressList) {
			if(email.getEmail() != null && !"".equals(email.getEmail().trim())) {
				Matcher matcher = emailIdPatter.matcher(email.getEmail());
				if(!matcher.matches()) {
		        	throw new InvalidInputException("Invalid Email Id provided. Email Id provided : " + email.getEmail());
		        }
			}
		}
		
	}

}
