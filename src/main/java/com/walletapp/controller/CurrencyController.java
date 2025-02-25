package com.walletapp.controller;

import com.walletapp.grpc.GrpcClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final GrpcClientService grpcClientService;

    public CurrencyController(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }

    @GetMapping("/convert")
    public double convertCurrency(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to) {
        return grpcClientService.convertCurrency(amount, from, to);
    }
}
