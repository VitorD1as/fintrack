package io.vitor.fintrack.database.mappers;

import io.vitor.fintrack.database.models.Budget;
import io.vitor.fintrack.database.models.dtos.BudgetRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    Budget toEntity(BudgetRequestDTO dto);

}
