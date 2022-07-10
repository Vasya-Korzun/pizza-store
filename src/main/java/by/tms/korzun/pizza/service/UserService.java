package by.tms.korzun.pizza.service;

import by.tms.korzun.pizza.configuration.security.UserRole;
import by.tms.korzun.pizza.entity.Status;
import by.tms.korzun.pizza.entity.UserEntity;
import by.tms.korzun.pizza.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public String registration(UserEntity user) {
			user.setRole(UserRole.CUSTOMER);
			user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
			user.setStatus(Status.ACTIVE);
			UserEntity savedUserEntity = userRepository.save(user);
			return savedUserEntity.getId().toString();
	}

	public UserEntity findByUsername(String username) {
		UserEntity byUsername = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User with username: " + username + " not found"));
		return byUsername;
	}

	public boolean existByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public boolean existByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

}
