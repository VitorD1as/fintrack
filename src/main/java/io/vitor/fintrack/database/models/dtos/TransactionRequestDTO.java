package io.vitor.fintrack.database.models.dtos;

import io.vitor.fintrack.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequestDTO(
        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O valor é obrigatório.")
        @Positive(message = "O valor deve ser mais que zero!")
        BigDecimal amount,

        @NotNull(message = "A data é obrigatória!")
        LocalDateTime date,

        @NotNull(message = "O tipo de transação é obrigatório!")
        TransactionType type,

        @NotNull(message = "A categoria é obrigatória!")
        Long categoryId,

        @NotNull(message = "A conta é obrigatória.")
        Long accountId
        ) {
}
