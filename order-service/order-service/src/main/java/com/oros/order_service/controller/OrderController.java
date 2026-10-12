package com.oros.order_service.controller;

import com.oros.order_service.dto.OrderRequest;
import com.oros.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    @CircuitBreaker(name = "orderServiceCircuitBreaker", fallbackMethod = "placeOrderFallback")
    @TimeLimiter(name = "orderServiceTimeLimiter")
    @Retry(name = "orderServiceRetry")
    public CompletableFuture<String> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return CompletableFuture.supplyAsync(() -> "Order placed successfully.");
    }

    public CompletableFuture<String> placeOrderFallback(OrderRequest orderRequest, Throwable throwable) {
        return CompletableFuture.supplyAsync(()-> "Order service is currently unavailable. Please try again later.");
    }
}
