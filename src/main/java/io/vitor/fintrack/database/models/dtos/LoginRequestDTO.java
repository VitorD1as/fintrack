package io.vitor.fintrack.database.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
}