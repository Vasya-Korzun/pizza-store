package by.tms.korzun.pizza.configuration.security;


import by.tms.korzun.pizza.entity.UserEntity;
import by.tms.korzun.pizza.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoadUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = userRepository.findByUsername(userName);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + userName + " not found");
        } else {
            final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                "ROLE_" + userEntity.get().getRole().name());
            return new User(userName, userEntity.get().getEncodedPassword(), List.of(authority));
        }
    }
}
