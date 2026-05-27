package io.vitor.fintrack.database.mappers;

import io.vitor.fintrack.database.models.Account;
import io.vitor.fintrack.database.models.dtos.AccountRequestDTO;
import io.vitor.fintrack.database.models.dtos.AccountResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "initialBalance", target = "currentBalance")
    @Mapping(source = "name", target = "nameAccount")
    @Mapping(source = "accountType", target = "type")
    Account toEntity(AccountRequestDTO requestDTO);

    @Mapping(source = "nameAccount", target = "name")
    @Mapping(source = "currentBalance", target = "balance")
    @Mapping(source = "type", target = "accountType")
    AccountResponseDTO toResponseDTO(Account account);

    List<AccountResponseDTO> toResponseDTOList(List<Account> accounts);
}