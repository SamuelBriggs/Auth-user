package com.example.stagetwohng.security.user;

import com.example.stagetwohng.exceptions.HngException;
import com.example.stagetwohng.model.User;
import com.example.stagetwohng.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@AllArgsConstructor
@Primary
public class HngUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;


    @SneakyThrows
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> member = userRepository.findByEmail(username);
            User foundUser = member.orElseThrow(() -> new HngException("Member Not Found"));
            UserDetails userDetails = new HngUserDetails(foundUser);
            return userDetails;
        }
}
