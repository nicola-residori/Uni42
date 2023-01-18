package com.uni42.authenticator.repository;

import com.uni42.authenticator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
