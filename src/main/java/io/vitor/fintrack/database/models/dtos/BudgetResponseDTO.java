package io.vitor.fintrack.database.models.dtos;

import java.math.BigDecimal;

public record BudgetResponseDTO(
        Long id,
        Long categoryId,
        String categoryName,
        BigDecimal limitAmount,
        BigDecimal amountSpent,
        BigDecimal amountAvailable,
        BigDecimal percentageUsed
) {}