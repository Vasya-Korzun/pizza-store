package by.tms.korzun.pizza.service;


import by.tms.korzun.pizza.configuration.security.JwtUtil;
import by.tms.korzun.pizza.configuration.security.UserRole;
import by.tms.korzun.pizza.dto.AuthRequest;
import by.tms.korzun.pizza.dto.UserSignInResponse;
import by.tms.korzun.pizza.entity.UserEntity;
import by.tms.korzun.pizza.exceptions.NoSuchUserException;
import by.tms.korzun.pizza.exceptions.SuchUserAlreadyExistException;
import by.tms.korzun.pizza.mapper.UserMapper;
import by.tms.korzun.pizza.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserSignInResponse signIn(final AuthRequest authRequest) throws NoSuchUserException {

        final UserEntity userEntity = userRepository
            .findByUsername(authRequest.getUsername())
            .orElseThrow(() -> new NoSuchUserException(
                "No user with email = " + authRequest.getUsername()+ " was found."));

        final UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(authentication);

        final User admin = getUserDetails(userEntity);
        return new UserSignInResponse(jwtUtil.generateToken(admin));
    }

    private User getUserDetails(final UserEntity userEntity) {
        final String email = userEntity.getEmail();
        final String password = userEntity.getEncodedPassword();
        final List<SimpleGrantedAuthority> authorities =
            List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
        return new User(email, password, authorities);
    }

    public UserSignInResponse signUp(final by.tms.korzun.pizza.dto.User userDTO)
            throws SuchUserAlreadyExistException, NoSuchUserException {

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new SuchUserAlreadyExistException("User with email " + userDTO.getUsername() + " already exists");
        }
        saveUser(userDTO);
        return signIn(new AuthRequest(userDTO.getUsername(), userDTO.getPassword()));
    }

    private void saveUser(final by.tms.korzun.pizza.dto.User userDTO) {
        final UserEntity userEntity = userMapper.sourceToDestination(userDTO);
        userEntity.setRole(UserRole.CUSTOMER);
        userEntity.setEncodedPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
    }

    public List<by.tms.korzun.pizza.dto.User> customers() {
        return userRepository.findAll()
                .stream()
                .filter(el -> el.getRole().equals(UserRole.CUSTOMER))
                .map(userMapper::destinationToSource)
                .collect(Collectors.toList());
    }
}
