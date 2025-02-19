package com.walletapp.model.transaction;

import jakarta.persistence.Embeddable;

public enum ParticipantRole {
    SENDER,
    RECEIVER,
    SELF
}
