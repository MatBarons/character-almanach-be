package com.character_almanach.model.user;

import java.util.HashSet;
import java.util.Set;

import com.character_almanach.model.character.Character;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserPrimaryKeys.class)
public class User {

    @Setter
    @OneToMany(
        mappedBy = "user", 
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    private Set<Roles> roles = new HashSet<>();

    @Setter
    @OneToMany(
        mappedBy = "user", 
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    private Set<Character> characters = new HashSet<>();
}
