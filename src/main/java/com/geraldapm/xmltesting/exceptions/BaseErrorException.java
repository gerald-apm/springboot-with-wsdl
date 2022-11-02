package com.geraldapm.xmltesting.exceptions;

import com.geraldapm.xmltesting.dto.ErrorMapper;

public class BaseErrorException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private ErrorMapper errorMapper;

    public BaseErrorException(){
        this.errorMapper.setErrorNum("0000");
        this.errorMapper.setErrorDescription("Default Error");
    }

    public BaseErrorException(String message) {
        super(message);
    }
    public BaseErrorException(String message, Throwable e) {
        super(message, e);
    }

    public BaseErrorException(ErrorMapper errorMapper) {
        super(errorMapper.getErrorNum());
        this.errorMapper = errorMapper;
    }
    public BaseErrorException(String message, Throwable e, ErrorMapper errorMapper) {
        super(message, e);
        this.errorMapper = errorMapper;
    }
    public ErrorMapper getErrorMapper() {
        return errorMapper;
    }
    public void setErrorMapper(ErrorMapper setErrorMapper) {
        this.errorMapper = setErrorMapper;
    }
}
