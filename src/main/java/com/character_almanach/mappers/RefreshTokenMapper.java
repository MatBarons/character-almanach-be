package com.character_almanach.mappers;

import com.character_almanach.model.user.RefreshToken;

public final class RefreshTokenMapper {
    public static String toDto(RefreshToken token){
        return token.getToken();
    }
}
