package com.orange.mainservice.component;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("components")
@AllArgsConstructor
class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(componentService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<ComponentResponse> create(@Valid @RequestBody ComponentRequest request) {
        ComponentResponse created = componentService.add(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponse> edit(@PathVariable("id") Long id,
                                                          @Valid @RequestBody ComponentRequest request){
        ComponentResponse edited = componentService.edit(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        componentService.delete(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/search/findByCategoryId/{id}")
    public ResponseEntity<Set<ComponentResponse>> getByCategoryId(@PathVariable("id") Long id){
        return ResponseEntity.ok(componentService.getByCategoryId(id));
    }
}
