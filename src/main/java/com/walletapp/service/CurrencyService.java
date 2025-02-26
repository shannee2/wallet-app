package com.walletapp.service;


import com.walletapp.grpc.GrpcClientService;
import com.walletapp.model.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private final GrpcClientService grpcClientService;

    public CurrencyService(GrpcClientService grpcClientService) {
        this.grpcClientService = grpcClientService;
    }

    public Money convertCurrency(Money money, String toCurrency){
        if(money.getCurrency().equals(toCurrency)){
            return money;
        }
        com.currency.proto.Money convertedAmount = grpcClientService.convertCurrency(money.getAmount(), money.getCurrency(), toCurrency);
        return new Money(convertedAmount.getAmount(), toCurrency);
    }
}
