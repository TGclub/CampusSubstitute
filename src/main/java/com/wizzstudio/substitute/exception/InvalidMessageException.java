package com.wizzstudio.substitute.exception;

import com.wizzstudio.substitute.constants.Constant;

public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException() {
        super(Constant.INVALID_MESSAGE);
    }

    public InvalidMessageException(String message) {
        super(message);
    }
}
