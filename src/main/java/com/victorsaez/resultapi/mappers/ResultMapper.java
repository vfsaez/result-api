package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.ResultDTO;
import com.victorsaez.resultapi.entities.Result;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultMapper {
    ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

    ResultDTO resultToResultDTO(Result result);
    Result resultDTOtoResult(ResultDTO resultDto);
}