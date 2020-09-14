package com.example.client.service;

import static java.util.stream.Collectors.toList;

import com.example.client.model.Portfolio;
import com.example.client.util.PortfolioUtil;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeightFactorServiceImpl implements WeightFactorService {
  private static final String CACHE_NAME = "portfolio-weight-factor-cache";

  private final CacheService cacheService;
  private final ForkJoinPool forkJoinPool;

  public WeightFactorServiceImpl(CacheService cacheService) {
    this.cacheService = cacheService;
    this.forkJoinPool = new ForkJoinPool(10);
  }

  @Async
  @Override
  public void calculateWeightFactor(String requestId, Integer portfolioId) {
    log.info("calculateWeightFactor: requestId: {}, portfolioId: {}", requestId, portfolioId);
    // Get portfolio
    final List<Portfolio> portfolios = PortfolioUtil.getPortfolio(portfolioId);
    // Calculate weight factor of each stock asynchronously
    final List<CompletableFuture<Portfolio>> portfolioFutures =
        portfolios.stream().map(this::calculateStockWeightFactor).collect(toList());
    // Create a combined Future using allOf()
    final CompletableFuture<Void> allFutures =
        CompletableFuture.allOf(
            portfolioFutures.toArray(new CompletableFuture[portfolioFutures.size()]));
    // When all the Futures are completed, call `future.join()` to get their results and collect the
    // results in a list
    final CompletableFuture<List<Portfolio>> allPortfolioFuture =
        allFutures.thenApply(
            v -> portfolioFutures.stream().map(CompletableFuture::join).collect(toList()));
    // Store the computed weight factor in cache
    allPortfolioFuture.thenAccept(
        pfs -> {
          log.info(
              "About to cache weight factor: requestId: {}, size: {}",
              requestId,
              CollectionUtils.size(pfs));
          cacheService.addToCache(CACHE_NAME, requestId, pfs);
        });
  }

  @Override
  public List<Portfolio> getWeightFactor(String requestId) {
    return cacheService.getValueFromCache(CACHE_NAME, requestId, List.class);
  }

  private CompletableFuture<Portfolio> calculateStockWeightFactor(Portfolio portfolio) {
    return CompletableFuture.supplyAsync(() -> marketCapCalc(portfolio), forkJoinPool)
        .thenApply(this::weightFactorCalc);
  }

  private Portfolio marketCapCalc(Portfolio portfolio) {
    log.debug("marketCapCalc: StockId: {}", portfolio.getStockId());
    return Portfolio.builder()
        .stockId(portfolio.getStockId())
        .shares(portfolio.getShares())
        .price(portfolio.getPrice())
        .marketCap(portfolio.getShares() * portfolio.getPrice())
        .build();
  }

  private Portfolio weightFactorCalc(Portfolio portfolio) {
    log.debug("weightFactorCalc: StockId: {}", portfolio.getStockId());
    final Double weightFactor =
        Math.abs(
            Math.tan(
                Math.tan(
                    Math.tan(Math.tan(Math.tan(Math.tan(Math.tan(portfolio.getMarketCap()))))))));
    return Portfolio.builder()
        .stockId(portfolio.getStockId())
        .shares(portfolio.getShares())
        .price(portfolio.getPrice())
        .marketCap(portfolio.getShares() * portfolio.getPrice())
        .weightFactor(weightFactor)
        .build();
  }
}
