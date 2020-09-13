package com.example.client.repository;

import com.example.client.model.Stock;
import java.util.List;
import org.springframework.data.hazelcast.repository.HazelcastRepository;

public interface StockRepository extends HazelcastRepository<Stock, String> {
  List<Stock> findAll();

  List<Stock> findByStockName(String stockName);
}
