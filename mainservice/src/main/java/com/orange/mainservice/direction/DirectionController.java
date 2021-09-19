package com.orange.mainservice.direction;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("directions")
@AllArgsConstructor
class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/{id}")
    public ResponseEntity<DirectionResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(directionService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<DirectionResponse> create(@Valid @RequestBody DirectionRequest request) {
        DirectionResponse created = directionService.createDirection(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectionResponse> edit(@PathVariable("id") Long id,
                                                  @Valid @RequestBody DirectionRequest request){
        DirectionResponse edited = directionService.editDirection(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        directionService.deleteDirection(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/findByRecipeId/{id}")
    public ResponseEntity<Set<DirectionResponse>> getByRecipeId(@PathVariable Long id){
        Set<DirectionResponse> directions= directionService.getByRecipeId(id);
        return ResponseEntity.ok(directions);
    }
}
