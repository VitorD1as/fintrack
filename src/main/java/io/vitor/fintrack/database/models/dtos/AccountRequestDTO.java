package io.vitor.fintrack.database.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record AccountRequestDTO(
        @NotBlank(message = "O nome da conta é obrigatório.")
        String name,

        @NotBlank(message = "O tipo de conta é obrigatório (ex: CHECKING, SAVINGS).")
        String accountType,

        @NotNull(message = "O saldo inicial não pode ser nulo.")
        @PositiveOrZero(message = "O saldo inicial deve ser maior ou igual a zero.")
        BigDecimal initialBalance
) {}