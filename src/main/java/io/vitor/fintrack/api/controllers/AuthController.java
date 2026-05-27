package io.vitor.fintrack.api.controllers;

import io.vitor.fintrack.database.models.dtos.LoginRequestDTO;
import io.vitor.fintrack.database.models.dtos.RegisterRequestDTO;
import io.vitor.fintrack.database.models.dtos.TokenResponseDTO;
import io.vitor.fintrack.exception.BadRequestException;
import io.vitor.fintrack.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) throws BadRequestException {
        authenticationService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws Exception {
        return authenticationService.login(loginRequestDTO);
    }
}