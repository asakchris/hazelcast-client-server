package com.example.client.service;

import com.example.client.model.Portfolio;
import java.util.List;

public interface WeightFactorService {
  void calculateWeightFactor(String requestId, Integer portfolioId);

  List<Portfolio> getWeightFactor(String requestId);
}
