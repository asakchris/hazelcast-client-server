package com.example.client.util;

import static java.util.stream.Collectors.toList;

import com.example.client.model.Portfolio;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortfolioUtil {
  public static List<Portfolio> getPortfolio(Integer portfolioId) {
    log.info("portfolioId: {}", portfolioId);
    int size = ((portfolioId % 5) + 1) * 100;
    log.info("size: {}", size);
    return IntStream.rangeClosed(1, size)
        .boxed()
        .map(PortfolioUtil::createPortfolio)
        .collect(toList());
  }

  private static Portfolio createPortfolio(Integer stockId) {
    return Portfolio.builder().stockId(stockId).price(getPrice()).shares(getShares()).build();
  }

  public static double getPrice() {
    return ThreadLocalRandom.current().nextDouble(1, 15);
  }

  public static long getShares() {
    return ThreadLocalRandom.current().nextLong(1000, 10000);
  }
}
