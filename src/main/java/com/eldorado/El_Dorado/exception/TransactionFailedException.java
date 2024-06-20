package com.eldorado.El_Dorado.exception;

import jakarta.transaction.TransactionRolledbackException;

import java.io.Serial;

public class TransactionFailedException extends TransactionRolledbackException {
    @Serial
    private static final long serialVersionUID = 2L;

    public TransactionFailedException() {
    }

    public TransactionFailedException(String msg) {
        super(msg);
    }
}
