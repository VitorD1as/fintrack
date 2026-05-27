package io.vitor.fintrack.services;

import io.vitor.fintrack.database.mappers.BudgetMapper;
import io.vitor.fintrack.database.models.Budget;
import io.vitor.fintrack.database.models.Category;
import io.vitor.fintrack.database.models.Transaction;
import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.database.models.dtos.BudgetRequestDTO;
import io.vitor.fintrack.database.models.dtos.BudgetResponseDTO;
import io.vitor.fintrack.database.repository.BudgetRepository;
import io.vitor.fintrack.database.repository.CategoryRepository;
import io.vitor.fintrack.database.repository.TransactionRepository;
import io.vitor.fintrack.enums.TransactionType;
import io.vitor.fintrack.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetMapper mapper;

    @Transactional
    public void createOrUpdateBudget(BudgetRequestDTO dto) throws NotFoundException {
        User user = userAuth();

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada!"));

        Budget budget = budgetRepository
                .findByUserAndCategoryAndTargetMonth(user, category, dto.targetMonth())
                .orElse(new Budget());

        budget.setUser(user);
        budget.setCategory(category);
        budget.setLimitAmount(dto.limitAmount());
        budget.setTargetMonth(dto.targetMonth());

        budgetRepository.save(budget);
    }

    public List<BudgetResponseDTO> getMonthlyBudgetReport(YearMonth yearMonth){
        User authUser = userAuth();

        List<Budget> budgets = budgetRepository.findByUserAndTargetMonth(authUser, yearMonth);

        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        return budgets.stream()
                .map(budget -> {
                    List<Transaction> expenses = transactionRepository
                            .findByUserAndCategoryAndTransactionTypeAndDateBetween(
                                    authUser, budget.getCategory(), TransactionType.EXPENSE, startOfMonth, endOfMonth
                            );

                    BigDecimal amountSpent = expenses.stream()
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal amountAvailable = budget.getLimitAmount().subtract(amountSpent);

                    BigDecimal percentageUsed = budget.getLimitAmount()
                            .compareTo(BigDecimal.ZERO) > 0 ? amountSpent.multiply(BigDecimal.valueOf(100))
                            .divide(budget.getLimitAmount(), 2, RoundingMode.HALF_UP) :
                            BigDecimal.ZERO;

                    return new BudgetResponseDTO(
                            budget.getId(),
                            budget.getCategory().getId(),
                            budget.getCategory().getName(),
                            budget.getLimitAmount(),
                            amountSpent,
                            amountAvailable,
                            percentageUsed
                    );
                })
                .toList();
                }

    private static User userAuth() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
