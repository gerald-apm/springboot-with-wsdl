package com.geraldapm.xmltesting.dto;

import org.springframework.stereotype.Component;

@Component
public class ErrorMapper {
    public String errorNumber;
    public String errorDescription;
    public ErrorMapper(String errorNumber, String errorDescription){
        this.errorDescription = errorDescription;
        this.errorNumber = errorNumber;
    }
    public ErrorMapper(){
    }

    public String getErrorNum() {
        return errorNumber;
    }

    public void setErrorNum(String errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
