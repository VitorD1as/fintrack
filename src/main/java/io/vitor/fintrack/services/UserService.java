package io.vitor.fintrack.services;

import io.vitor.fintrack.database.mappers.UserMapper;
import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.database.models.dtos.UserDTO;
import io.vitor.fintrack.database.repository.UserRepository;
import io.vitor.fintrack.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO registerUser(UserDTO dto) throws BadRequestException {

        if(userRepository.existsByEmail(dto.email())){
            throw new BadRequestException("Email já cadastrado!");
        }

        User user = userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return userMapper.toDTO(user);
    }
}
