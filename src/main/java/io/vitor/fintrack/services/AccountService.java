package io.vitor.fintrack.services;

import io.vitor.fintrack.database.mappers.AccountMapper;
import io.vitor.fintrack.database.models.Account;
import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.database.models.dtos.AccountRequestDTO;
import io.vitor.fintrack.database.models.dtos.AccountResponseDTO;
import io.vitor.fintrack.database.repository.AccountRepository;
import io.vitor.fintrack.database.repository.UserRepository;
import io.vitor.fintrack.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final AccountMapper mapper;

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO accountDTO) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Usuário não encontrado!")
        );

        Account account = mapper.toEntity(accountDTO);

        account.setUser(user);

        Account savedAccount = repository.save(account);

        return mapper.toResponseDTO(account);
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> listAllMyAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<Account> accounts = repository.findByUserEmail(email);

        return mapper.toResponseDTOList(accounts);
    }


}
