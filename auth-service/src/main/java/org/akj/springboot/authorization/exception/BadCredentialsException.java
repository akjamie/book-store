package org.akj.springboot.authorization.exception;

import org.akj.springboot.common.exception.BusinessException;

public class BadCredentialsException extends BusinessException {
    public BadCredentialsException(String code, String message) {
        super(code, message);
    }
}
