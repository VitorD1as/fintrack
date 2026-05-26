package io.vitor.fintrack.database.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO (
    @NotBlank(message = "Nome não pode ser nulo!")
    @Size(max = 150)
    String nome,
    @NotBlank(message = "Email não pode ser nulo!")
    @Email(message = "Email inválido")
    String email,
    @NotBlank(message = "Senha não pode ser nula!")
    @Size(min = 4, message = "Senha precisa ter mais de 4 caractéres")
    String password
){
}
