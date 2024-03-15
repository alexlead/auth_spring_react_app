package com.securityserver.study.repository;

import com.securityserver.study.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);
}