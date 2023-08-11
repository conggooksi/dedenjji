package com.secondwind.dedenjji.common.exception;

import com.secondwind.dedenjji.common.result.JsonResultData;
import lombok.Getter;

@Getter
public class CustomAuthException extends RuntimeException{
    private JsonResultData errorEntity;

    public CustomAuthException(JsonResultData errorEntity) {
        super(errorEntity.getError().getMessage());
        this.errorEntity = errorEntity;
    }
}
