package com.orange.mainservice.rate;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("rates")
@AllArgsConstructor
class RateController {

    private final RateService rateService;

    @GetMapping("/{id}")
    public ResponseEntity<RateResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(rateService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<RateResponse> create(@Valid @RequestBody RateRequest request) {
        RateResponse created = rateService.createRate(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateResponse> edit(@PathVariable("id") Long id,
                                             @Valid @RequestBody RateRequest request) {
        RateResponse edited = rateService.editRate(id, request);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        rateService.deleteRate(id);
        return ResponseEntity.ok().build();
    }
}
