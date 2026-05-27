package io.vitor.fintrack.database.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO(
        Long id,
        String name,
        String accountType,
        BigDecimal balance,
        LocalDateTime createdAt
) {}