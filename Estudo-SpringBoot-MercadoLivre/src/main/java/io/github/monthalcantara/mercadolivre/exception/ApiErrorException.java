package io.github.monthalcantara.mercadolivre.exception;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final String reason;

    /**
     *
     * @param httpStatus
     * @param reason Descritivo da exception
     */
    public ApiErrorException(HttpStatus httpStatus, String reason) {
        super(reason);
        this.httpStatus = httpStatus;
        this.reason = reason;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getReason() {
        return reason;
    }
}
