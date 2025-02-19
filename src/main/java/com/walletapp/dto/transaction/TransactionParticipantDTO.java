package com.walletapp.dto.transaction;

import com.walletapp.model.transaction.ParticipantRole;

public class TransactionParticipantDTO {
    private Long userId;
    private Long walletId;
    private ParticipantRole role;

    public TransactionParticipantDTO( Long userId, Long walletId, ParticipantRole role) {
        this.userId = userId;
        this.walletId = walletId;
        this.role = role;
    }

    public TransactionParticipantDTO(){}



//    public Long getParticipantId() {
//        return participantId;
//    }

    public Long getUserId() {
        return userId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public ParticipantRole getRole() {
        return role;
    }
}
