package io.linear.domain.dto.userDto;

import lombok.Data;

@Data
public class RegisterDto {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
}
