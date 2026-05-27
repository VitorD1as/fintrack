package io.vitor.fintrack.database.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterRequestDTO {
    @NotBlank
    private String name;
    @NotBlank(message = "Email precisa ser válido!")
    private String email;
    @NotBlank
    private String senha;
}