package com.character_almanach.model.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class UserPrimaryKeys{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String email;
    private String username;
}
