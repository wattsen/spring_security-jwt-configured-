package io.linear.service.impl;

import io.linear.domain.dto.userDto.RegisterDto;
import io.linear.exception.ValidationException;
import io.linear.service.UserValidationService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements UserValidationService {

	private static final String REGEX_EMAIL = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
	private static final String REGEX_PHONE = "^\\+(?:[0-9] ?){6,14}[0-9]$";

	@Override
	public void validateUserRegistration(RegisterDto registerDto) throws ValidationException {
		var validationErrors = new ArrayList<String>();

		var username = registerDto.getUsername();
		var password = registerDto.getPassword();
		var firstName = registerDto.getFirstName();
		var lastName = registerDto.getLastName();
		var email = registerDto.getEmail();
		var phoneNumber = registerDto.getPhoneNumber();

		if (Strings.isBlank(username)) {
			validationErrors.add("Invalid Username");
		} else if (username.length() <= 5) {
			validationErrors.add("Username must contain more than 5 character");
		}

		if (Strings.isBlank(password)) {
			validationErrors.add("Invalid Password");
		} else if (password.length() <= 6) {
			validationErrors.add("Password must contain more than 6 characters");
		}

		if (Strings.isBlank(firstName) || Strings.isBlank(lastName)) {
			validationErrors.add("Invalid last_name or first_name");
		}

		if(Strings.isBlank(email) || !patternMatches(email, REGEX_EMAIL)) {
			validationErrors.add("Invalid Email");
		}

		if(Strings.isBlank(phoneNumber) || !patternMatches(phoneNumber, REGEX_PHONE)) {
			validationErrors.add("Invalid phone number");
		}

		if (!validationErrors.isEmpty()) {
			throw new ValidationException(validationErrors);
		}

	}

	public static boolean patternMatches(String value, String regexPattern) {
		return Pattern.compile(regexPattern)
				.matcher(value)
				.matches();
	}
}
