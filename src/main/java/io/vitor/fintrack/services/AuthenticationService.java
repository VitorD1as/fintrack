package io.vitor.fintrack.services;

import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.database.models.dtos.LoginRequestDTO;
import io.vitor.fintrack.database.models.dtos.RegisterRequestDTO;
import io.vitor.fintrack.database.models.dtos.TokenResponseDTO;
import io.vitor.fintrack.database.repository.UserRepository;
import io.vitor.fintrack.exception.BadRequestException;
import io.vitor.fintrack.security.TokenProvider;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${spring.jwt.expiration}")
    private Long expirationTime;

    public void register(@NotNull RegisterRequestDTO registerRequestDTO) throws BadRequestException {
        User user = userRepository.findByEmail(registerRequestDTO.getEmail())
                .orElse(null);

        if(user != null){
            throw new BadRequestException("Esse aluno já está cadastrado!");
        }

        userRepository.save(User.builder()
                        .name(registerRequestDTO.getName())
                        .email(registerRequestDTO.getEmail())
                        .password(passwordEncoder.encode(registerRequestDTO.getSenha()))
                .build());
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO){
        try{
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getSenha()));

            String token = tokenProvider.generateToken(authentication);
            return new TokenResponseDTO(token, expirationTime);
        } catch (Exception e){
            throw e;
        }
    }
}
