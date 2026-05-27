package io.vitor.fintrack.database.models.dtos;

public record TokenResponseDTO(String token, long expiresIn) {
}
