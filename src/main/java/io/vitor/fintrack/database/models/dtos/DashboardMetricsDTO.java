package io.vitor.fintrack.database.models.dtos;

import java.math.BigDecimal;

public record DashboardMetricsDTO(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal netBalance,      // Lucro ou Prejuízo líquido (Income - Expense)
        BigDecimal averageDailyExpense // Média de gastos por dia
) {}