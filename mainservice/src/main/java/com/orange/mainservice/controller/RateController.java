package com.orange.mainservice.controller;

import com.orange.mainservice.response.RateResponse;
import com.orange.mainservice.service.RateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rates")
@AllArgsConstructor
public class RateController {

    private final RateService rateService;

    @GetMapping("/{id}")
    public ResponseEntity<RateResponse> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(rateService.getResponseById(id));
    }
}
