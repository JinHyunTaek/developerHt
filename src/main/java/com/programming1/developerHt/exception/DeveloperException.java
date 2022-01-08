package com.programming1.developerHt.exception;

import com.programming1.developerHt.entity.Developer;
import lombok.Getter;

@Getter
public class DeveloperException extends RuntimeException{
    private DeveloperErrorCode developerErrorCode;
    private String detailMessage;

    //생성자1. DeveloperErrorCode(enum)에서 생성한 기본메세지
    public DeveloperException(DeveloperErrorCode errorCode){
        super(errorCode.getMessage());
        this.developerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    //생성자2. 내가 설정한 메세지
    public DeveloperException(DeveloperErrorCode errorCode,String detailMessage){
        super(detailMessage);
        this.developerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
