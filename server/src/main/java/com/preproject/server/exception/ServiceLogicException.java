package com.preproject.server.exception;

import com.preproject.server.constant.ErrorCode;
import lombok.Getter;

@Getter
public class ServiceLogicException extends RuntimeException {

    private ErrorCode errorCode;

    public ServiceLogicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}