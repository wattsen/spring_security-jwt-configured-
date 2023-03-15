package io.linear.service;

import io.linear.domain.dto.userDto.RegisterDto;
import io.linear.exception.ValidationException;

public interface UserValidationService {

	void validateUserRegistration(RegisterDto registerDto) throws ValidationException;
}
