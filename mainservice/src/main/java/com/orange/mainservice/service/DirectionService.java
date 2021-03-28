package com.orange.mainservice.service;

import com.orange.mainservice.entity.Direction;
import com.orange.mainservice.mapper.response.DirectionResponseMapper;
import com.orange.mainservice.repository.DirectionRepository;
import com.orange.mainservice.response.DirectionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class DirectionService {

    private final DirectionRepository directionRepository;
    private final DirectionResponseMapper responseMapper;

    public DirectionResponse getResponseById(Long id){
        return responseMapper.directionToDto(getById(id));
    }

    private Direction getById(Long id){
        return directionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
