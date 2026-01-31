package com.character_almanach.dto.get;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {
    public String error;
    public String message;
}
