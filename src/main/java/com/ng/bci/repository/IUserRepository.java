package com.ng.bci.repository;

import com.ng.bci.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);
  Optional<User> findByName(String name);
}
