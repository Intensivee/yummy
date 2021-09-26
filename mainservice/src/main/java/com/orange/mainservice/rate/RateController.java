package com.orange.mainservice.rate;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("rates")
@AllArgsConstructor
class RateController {

    private final RateService rateService;

    @PostMapping
    public ResponseEntity<Double> crateOrEditRate(@Valid @RequestBody RateRequest request) {
        return ResponseEntity.ok(rateService.crateOrEditRate(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        rateService.deleteRate(id);
        return ResponseEntity.ok().build();
    }
}
