package io.vitor.fintrack.database.repository;

import io.vitor.fintrack.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
}
