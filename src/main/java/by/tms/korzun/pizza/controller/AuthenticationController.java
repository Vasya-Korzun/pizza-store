package by.tms.korzun.pizza.controller;

import by.tms.korzun.pizza.configuration.security.JwtUtil;
import by.tms.korzun.pizza.dto.AuthRequest;
import by.tms.korzun.pizza.dto.User;
import by.tms.korzun.pizza.dto.UserSignInResponse;
import by.tms.korzun.pizza.exceptions.NoSuchUserException;
import by.tms.korzun.pizza.exceptions.SuchUserAlreadyExistException;
import by.tms.korzun.pizza.mapper.UserMapper;
import by.tms.korzun.pizza.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserSignInResponse> login(@RequestBody AuthRequest requestDto) throws NoSuchUserException {
        UserSignInResponse response = authService.signIn(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user) throws NoSuchUserException, SuchUserAlreadyExistException {
        authService.signUp(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<Object, Object>> logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<Object, Object> resp = new HashMap<>();

        if (auth != null) {
            resp.put("username", auth.getName());
            resp.put("session, lastAccessedTime", session.getLastAccessedTime());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
