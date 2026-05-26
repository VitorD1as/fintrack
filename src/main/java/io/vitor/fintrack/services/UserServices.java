package io.vitor.fintrack.services;

import io.vitor.fintrack.database.mappers.UserMapper;
import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.database.models.dtos.UserDTO;
import io.vitor.fintrack.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO registerUser(UserDTO dto){

        if(userRepository.existsByEmail(dto.email())){
            throw new RuntimeException("Email já cadastrado!");
        }

        User user = userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return userMapper.toDTO(user);
    }
}
