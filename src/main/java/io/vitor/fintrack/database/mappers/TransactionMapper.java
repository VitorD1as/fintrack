package io.vitor.fintrack.database.mappers;

import io.vitor.fintrack.database.models.Transaction;
import io.vitor.fintrack.database.models.dtos.TransactionRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionRequestDTO toDTO(Transaction transaction);

    Transaction toEntity(TransactionRequestDTO transactionRequestDTO);
}
