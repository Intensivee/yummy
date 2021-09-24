package com.orange.mainservice.component;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("components")
@AllArgsConstructor
class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(componentService.getResponseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ComponentResponse>> getAllByIsAcceptedAndIsReviewed(@RequestParam boolean isAccepted,
                                                                                   @RequestParam boolean isReviewed) {
        return ResponseEntity.ok(componentService.findAllByIsAcceptedAndIsReviewed(isAccepted, isReviewed));
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllNamesOrdered() {
        return ResponseEntity.ok(componentService.getAllNamesOrdered());
    }

    @GetMapping("/search/findByCategoryId/{id}")
    public ResponseEntity<Set<ComponentResponse>> getByCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(componentService.getByCategoryId(id));
    }

    @PostMapping
    public ResponseEntity<ComponentResponse> create(@Valid @RequestBody ComponentRequest request) {
        ComponentResponse created = componentService.createComponent(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponse> edit(@PathVariable Long id,
                                                  @Valid @RequestBody ComponentRequest request) {
        ComponentResponse edited = componentService.editComponent(id, request);
        return ResponseEntity.ok(edited);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ComponentResponse> patch(@PathVariable Long id,
                                                   @Valid @RequestBody ComponentRequest request) {
        ComponentResponse patchedComponent = componentService.patchComponent(id, request);
        return ResponseEntity.ok(patchedComponent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.ok().build();
    }
}
