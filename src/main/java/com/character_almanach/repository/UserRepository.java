package com.character_almanach.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.character_almanach.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User save(User user);
    public Optional<User> findById(Long id);
    
}
