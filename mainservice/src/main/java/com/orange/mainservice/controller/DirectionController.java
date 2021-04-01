package com.orange.mainservice.controller;

import com.orange.mainservice.request.DirectionRequest;
import com.orange.mainservice.response.DirectionResponse;
import com.orange.mainservice.service.DirectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("directions")
@AllArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/{id}")
    public ResponseEntity<DirectionResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(directionService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<DirectionResponse> create(@Valid @RequestBody DirectionRequest request){
        DirectionResponse created = directionService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getDirectionId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectionResponse> edit(@PathVariable("id") Long id,
                                                  @Valid @RequestBody DirectionRequest request){
        DirectionResponse edited = directionService.edit(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        directionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
