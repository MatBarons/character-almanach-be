package com.character_almanach.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.character_almanach.model.character.GameCharacter;
import com.character_almanach.model.character.CharacterClass;
import com.character_almanach.model.character.Stats;
import com.character_almanach.repository.CharacterRepository;

@Component
@Profile("test")
@Order(1)
public class DataLoader implements CommandLineRunner {
    private final CharacterRepository characterRepository;

    public DataLoader(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stats stats1 = new Stats(16, 14, 15, 12, 10, 13);
        GameCharacter character1 = new GameCharacter("Arthas", 5, "Human", stats1);
        character1.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character1.addClass(new CharacterClass("Warrior", "Battle Master", 2));

        Stats stats2 = new Stats(10, 18, 12, 14, 13, 8);
        GameCharacter character2 = new GameCharacter("Lyra", 4, "Elf", stats2);
        character2.addClass(new CharacterClass("Rogue", "Assassin", 4));

        Stats stats3 = new Stats(8, 12, 10, 18, 16, 14);
        GameCharacter character3 = new GameCharacter("Merlin", 7, "Human", stats3);
        character3.addClass(new CharacterClass("Wizard", "School of Evocation", 7));
        characterRepository.save(character1);
        characterRepository.save(character2);
        characterRepository.save(character3);

        System.out.println("Sample characters loaded into the database.");
    }
}

