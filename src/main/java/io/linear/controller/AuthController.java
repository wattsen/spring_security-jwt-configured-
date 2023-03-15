package io.linear.controller;

import io.linear.domain.dto.AuthResponseDto;
import io.linear.domain.dto.userDto.LoginDto;
import io.linear.domain.dto.userDto.RegisterDto;
import io.linear.domain.entity.Role;
import io.linear.domain.entity.UserEntity;
import io.linear.domain.entity.enums.UserRoleEnum;
import io.linear.exception.ValidationException;
import io.linear.repository.RoleRepository;
import io.linear.repository.UserRepository;
import io.linear.security.JwtGenerator;
import io.linear.service.UserValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtGenerator jwtGenerator;
	private final UserValidationService validationService;

	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator, UserValidationService validationService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
		this.validationService = validationService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {

		if(userRepository.existsByUsername(registerDto.getUsername()) || userRepository.existsByEmail(registerDto.getEmail())){
			return new ResponseEntity<>(
					"Username or Email is taken",
					HttpStatus.BAD_REQUEST
			);
		}

		try {
			validationService.validateUserRegistration(registerDto);
			UserEntity user = new UserEntity();
			user.setUsername(registerDto.getUsername());
			user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
			user.setFirstName(registerDto.getFirstName());
			user.setLastName(registerDto.getLastName());
			user.setEmail(registerDto.getEmail());
			user.setPhoneNumber(registerDto.getPhoneNumber());

			Role roles = roleRepository.findByRole(UserRoleEnum.CLIENT).get();
			user.setRoles(Collections.singletonList(roles));

			userRepository.save(user);

			return new ResponseEntity<>(
					"UserEntity was created",
					HttpStatus.OK
			);
		} catch (ValidationException e ) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(Map.of(
							"timestamp", OffsetDateTime.now(ZoneOffset.UTC),
							"errors", e.getErrorMessages()
					));
		}


	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsername(), loginDto.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<>(
				new AuthResponseDto(token),
				HttpStatus.OK
		);
	}
}
