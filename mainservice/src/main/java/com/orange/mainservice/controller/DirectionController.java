package com.orange.mainservice.controller;

import com.orange.mainservice.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectionController {

    private final DirectionService directionService;

    @Autowired
    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }
}
