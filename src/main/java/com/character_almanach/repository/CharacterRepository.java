package com.character_almanach.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.character_almanach.model.character.GameCharacter;

@Repository
public interface CharacterRepository extends JpaRepository<GameCharacter,Long>{
    public List<GameCharacter> findAll();
    @Query("SELECT c FROM GameCharacter c WHERE c.user.id = ?1")
    public List<GameCharacter> findByUserId(Long userId);
    public Optional<GameCharacter> findById(Long id);
    public GameCharacter save(GameCharacter character);
    public void deleteById(Long id);
}