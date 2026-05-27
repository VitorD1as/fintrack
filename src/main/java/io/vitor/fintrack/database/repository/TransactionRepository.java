package io.vitor.fintrack.database.repository;

import io.vitor.fintrack.database.models.Category;
import io.vitor.fintrack.database.models.Transaction;
import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserAndDateBetween(User user, LocalDateTime start, LocalDateTime end);

    List<Transaction> findByUserAndTransactionTypeAndDateBetween(User user, TransactionType type, LocalDateTime start, LocalDateTime end);

    List<Transaction> findByUserAndCategoryAndTransactionTypeAndDateBetween(
            User user,
            Category category,
            TransactionType type,
            LocalDateTime start,
            LocalDateTime end
    );
}
