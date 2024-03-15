package com.securityserver.study.service;

import com.securityserver.study.domain.User;
import com.securityserver.study.exception_handling.UserAlreadyExistsException;
import com.securityserver.study.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.securityserver.study.domain.Role;
@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return user;
    }


    public User register(User user) throws UserAlreadyExistsException {
        User foundUser = repository.findByUsername(user.getUsername());

        if (foundUser != null) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует!");
        }

        user.setId("0");
        Role role = Role.builder().id(0).name("ROLE_USER").build();
        user.setRole(role);

        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return repository.save(user);
    }


}
