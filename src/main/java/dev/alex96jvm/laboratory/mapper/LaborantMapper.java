package dev.alex96jvm.laboratory.mapper;

import dev.alex96jvm.laboratory.dto.LaborantDto;
import dev.alex96jvm.laboratory.model.LaborantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LaborantMapper {
    LaborantDto laborantEntityToLaborantDto(LaborantEntity laborantEntity);

    LaborantEntity laborantDtoToLaborantEntity(LaborantDto laborantDto);
}
