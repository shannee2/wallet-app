package com.walletapp.handler;

import com.walletapp.model.transaction.TransactionType;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WalletHandlerRegistry {

    private final Map<TransactionType, WalletHandler> handlers = new HashMap<>();
    private final List<WalletHandler> handlerList;

    public WalletHandlerRegistry(List<WalletHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @PostConstruct
    public void init() {
        for (WalletHandler handler : handlerList) {
            handlers.put(handler.getType(), handler);
        }
    }

    public WalletHandler getHandler(TransactionType type) {
        if(!handlers.containsKey(type)){
            throw new IllegalArgumentException("Invalid transaction type");
        }
        return handlers.get(type);
    }
}
