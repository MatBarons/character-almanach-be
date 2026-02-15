package com.character_almanach.exception.token;

public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException() {
        super("Refresh token expired");
    }
}
