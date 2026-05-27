package io.vitor.fintrack.database.repository;

import io.vitor.fintrack.database.models.Budget;
import io.vitor.fintrack.database.models.Category;
import io.vitor.fintrack.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserAndTargetMonth(User user, YearMonth targetMonth);

    Optional<Budget> findByUserAndCategoryAndTargetMonth(User user, Category category, YearMonth targetMonth);
}