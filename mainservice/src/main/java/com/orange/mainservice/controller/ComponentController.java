package com.orange.mainservice.controller;

import com.orange.mainservice.response.ComponentResponse;
import com.orange.mainservice.service.ComponentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("components")
@AllArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(componentService.getResponseById(id));
    }
}
