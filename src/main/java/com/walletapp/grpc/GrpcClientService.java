package com.walletapp.grpc;

import com.example.currency.proto.CurrencyConverterGrpc;
import com.example.currency.proto.ConvertRequest;
import com.example.currency.proto.ConvertResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {
    private final CurrencyConverterGrpc.CurrencyConverterBlockingStub currencyStub;

    public GrpcClientService() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        currencyStub = CurrencyConverterGrpc.newBlockingStub(channel);
    }

    public double convertCurrency(double amount, String from, String to) {
        ConvertRequest request = ConvertRequest.newBuilder()
                .setAmount(amount)
                .setFromCurrency(from)
                .setToCurrency(to)
                .build();

        ConvertResponse response = currencyStub.convertCurrency(request);
        return response.getConvertedAmount();
    }
}
