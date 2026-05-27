package io.vitor.fintrack.database.repository;

import io.vitor.fintrack.database.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserEmail(String email);
}
