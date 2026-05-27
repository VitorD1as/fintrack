package io.vitor.fintrack.services;

import io.vitor.fintrack.database.mappers.TransactionMapper;
import io.vitor.fintrack.database.models.Account;
import io.vitor.fintrack.database.models.Category;
import io.vitor.fintrack.database.models.Transaction;
import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.database.models.dtos.DashboardMetricsDTO;
import io.vitor.fintrack.database.models.dtos.TransactionRequestDTO;
import io.vitor.fintrack.database.repository.AccountRepository;
import io.vitor.fintrack.database.repository.CategoryRepository;
import io.vitor.fintrack.database.repository.TransactionRepository;
import io.vitor.fintrack.enums.TransactionType;
import io.vitor.fintrack.exception.BadRequestException;
import io.vitor.fintrack.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper mapper;

    @Transactional
    public void createTransaction(TransactionRequestDTO dto) throws NotFoundException, BadRequestException {
        User authUser = userAuth();

        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new NotFoundException("Conta não encontrada!"));

        if(!account.getUser().getId().equals(authUser.getId())){
            throw new BadRequestException("Acesso negado: essa conta não te pertence!");
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new BadRequestException("Categoria não encontrada!"));

        BigDecimal currentBalance = account.getCurrentBalance();

        if(dto.type() == TransactionType.EXPENSE){
            account.setCurrentBalance(currentBalance.subtract(dto.amount()));
        } else if(dto.type() == TransactionType.INCOME){
            account.setCurrentBalance(currentBalance.add(dto.amount()));
        }

        Transaction transaction = mapper.toEntity(dto);
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTodayTransactions(){
        User authUser = userAuth();

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        return transactionRepository.findByUserAndDateBetween(authUser, start, end);
    }

    @Transactional(readOnly = true)
    public DashboardMetricsDTO getMonthMetrics(YearMonth yearMonth){
        User auth = userAuth();

        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<Transaction> incomes = transactionRepository
                .findByUserAndTransactionTypeAndDateBetween(auth, TransactionType.INCOME, startOfMonth, endOfMonth);

        List<Transaction> expenses = transactionRepository
                .findByUserAndTransactionTypeAndDateBetween(auth, TransactionType.EXPENSE, startOfMonth, endOfMonth);

        BigDecimal totalIncome = incomes.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenses.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        BigDecimal averageDailyExpense = BigDecimal.ZERO;
        int daysInMonth = yearMonth.lengthOfMonth();

        if(totalExpense.compareTo(BigDecimal.ZERO) > 0){
            averageDailyExpense = totalExpense.divide(BigDecimal.valueOf(daysInMonth), 2, RoundingMode.HALF_UP);
        }

        return new DashboardMetricsDTO(totalIncome, totalExpense, netBalance, averageDailyExpense);
    }

    private static User userAuth() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
