package com.walletapp.grpc;

import com.currency.proto.*;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    private final CurrencyConverterGrpc.CurrencyConverterBlockingStub grpcStub;

    @Autowired
    public GrpcClientService(ManagedChannel managedChannel) {
        this.grpcStub = CurrencyConverterGrpc.newBlockingStub(managedChannel);
    }

    public Money convertCurrency(double amount, String fromCurrency, String toCurrency) {
        Money money = Money.newBuilder()
                .setAmount(amount)
                .setCurrency(fromCurrency)
                .build();

        ConvertRequest request = ConvertRequest.newBuilder()
                .setMoney(money)
                .setToCurrency(toCurrency)
                .build();

        ConvertResponse response = grpcStub.convertCurrency(request);

        return response.getConvertedMoney();
    }
}
