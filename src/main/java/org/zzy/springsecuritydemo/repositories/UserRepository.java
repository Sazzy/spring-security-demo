package org.zzy.springsecuritydemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zzy.springsecuritydemo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
