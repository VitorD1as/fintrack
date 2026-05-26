package io.vitor.fintrack.database.repository;

import io.vitor.fintrack.database.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
