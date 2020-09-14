package com.example.client.controller;

import com.example.client.model.Portfolio;
import com.example.client.service.WeightFactorService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/weight-factors")
@Slf4j
public class WeightFactorController {
  private final WeightFactorService service;

  public WeightFactorController(WeightFactorService service) {
    this.service = service;
  }

  @PostMapping(value = "/{portfolioId}")
  @ResponseStatus(code = HttpStatus.CREATED)
  public Map<String, String> triggerWeightFactorCalc(@PathVariable Integer portfolioId) {
    log.info("triggerWeightFactorCalc: portfolioId: {}", portfolioId);
    String requestId = UUID.randomUUID().toString();
    final Map<String, String> response = Collections.singletonMap("requestId", requestId);
    service.calculateWeightFactor(requestId, portfolioId);
    return response;
  }

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public List<Portfolio> getWeightFactor(@RequestParam String requestId) {
    log.info("getWeightFactor: requestId: {}", requestId);
    final Map<String, String> response = Collections.singletonMap("requestId", requestId);
    return service.getWeightFactor(requestId);
  }
}
