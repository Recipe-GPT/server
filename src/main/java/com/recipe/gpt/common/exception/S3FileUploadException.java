package com.recipe.gpt.common.exception;

import org.springframework.http.HttpStatus;

public class S3FileUploadException extends GeneralHttpException {

    public S3FileUploadException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 업로드하는 중 에러가 발생하였습니다.", null);
    }

}
