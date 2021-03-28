package com.orange.mainservice.controller;

import com.orange.mainservice.response.DirectionResponse;
import com.orange.mainservice.service.DirectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("directions")
@AllArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/{id}")
    public ResponseEntity<DirectionResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(directionService.getResponseById(id));
    }
}
