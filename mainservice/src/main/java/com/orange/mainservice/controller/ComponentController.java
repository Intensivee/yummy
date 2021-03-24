package com.orange.mainservice.controller;

import com.orange.mainservice.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComponentController {

    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }
}
