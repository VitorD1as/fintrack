package io.vitor.fintrack.database.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.YearMonth;

public record BudgetRequestDTO(
        @NotNull Long categoryId,
        @NotNull @Positive BigDecimal limitAmount,
        @NotNull YearMonth targetMonth
        ) {

}
