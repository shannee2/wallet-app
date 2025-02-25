//package com.walletapp.grpc.service;
//
//import com.walletapp.currency.*;
//import com.walletapp.model.money.Money;
//import com.walletapp.service.CurrencyService;
//import io.grpc.stub.StreamObserver;
//import net.devh.boot.grpc.server.service.GrpcService;
//
//
//@GrpcService
//public class CurrencyConverterServiceImpl extends CurrencyConverterServiceGrpc.CurrencyConverterServiceImplBase {
//
//    private final CurrencyService currencyService;
//
//    public CurrencyConverterServiceImpl(CurrencyService currencyService) {
//        this.currencyService = currencyService;
//    }
//
//    @Override
//    public void convertCurrency(CurrencyConversionRequest request, StreamObserver<CurrencyConversionResponse> responseObserver) {
//        Money money = new Money(request.getAmount(), currencyService.getCurrency(request.getFromCurrency()));
//        double convertedAmount = money.convertTo(currencyService.getCurrency(request.getToCurrency())).getAmount();
//
//        CurrencyConversionResponse response = CurrencyConversionResponse.newBuilder()
//                .setConvertedAmount(convertedAmount)
//                .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//}
