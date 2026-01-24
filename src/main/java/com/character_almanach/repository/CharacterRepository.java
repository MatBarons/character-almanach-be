package com.character_almanach.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.character_almanach.model.character.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character,Long>{
    public List<Character> findAll();
    @Query("SELECT c FROM Character c WHERE c.user.id = ?1")
    public List<Character> findByUserId(Long userId);
    public Optional<Character> findById(Long id);
    public Character save(Character character);
    public void deleteById(Long id);
}