package com.walletapp.registry;

import com.walletapp.dto.transaction.TransactionType;
import com.walletapp.handler.TransactionHandler;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransactionHandlerRegistry {

    private final Map<TransactionType, TransactionHandler> handlers = new HashMap<>();
    private final List<TransactionHandler> handlerList;

    public TransactionHandlerRegistry(List<TransactionHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @PostConstruct
    private void init() {
        for (TransactionHandler handler : handlerList) {
            handlers.put(handler.getType(), handler);
        }
    }

    public TransactionHandler getHandler(TransactionType type) {
        return handlers.get(type);
    }
}
