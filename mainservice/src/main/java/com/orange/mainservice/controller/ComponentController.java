package com.orange.mainservice.controller;

import com.orange.mainservice.request.ComponentRequest;
import com.orange.mainservice.response.ComponentResponse;
import com.orange.mainservice.service.ComponentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("components")
@AllArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(componentService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<ComponentResponse> create(@Valid @RequestBody ComponentRequest request) {
        ComponentResponse created = componentService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getComponentId()).toUri();
        return ResponseEntity.created(location).body(created);
    }
}
