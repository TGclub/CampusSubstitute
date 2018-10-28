package com.wizzstudio.substitute.exception;

import com.wizzstudio.substitute.constants.Constants;

public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException() {
        super(Constants.INVALID_MESSAGE);
    }

    public InvalidMessageException(String message) {
        super(message);
    }
}
