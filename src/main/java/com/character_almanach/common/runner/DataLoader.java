package com.character_almanach.common.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.character_almanach.model.CharacterClass;
import com.character_almanach.model.Stats;
import com.character_almanach.model.Character;
import com.character_almanach.repository.CharacterRepository;

@Component
@Order(1)
public class DataLoader implements CommandLineRunner {
    private final CharacterRepository characterRepository;

    public DataLoader(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stats stats1 = new Stats(16, 14, 15, 12, 10, 13);
        Character character1 = new Character("Arthas", 5, "Human", stats1);
        character1.addClass(new CharacterClass("Paladin", "Oath of the Ancients", 3));
        character1.addClass(new CharacterClass("Warrior", "Battle Master", 2));

        Stats stats2 = new Stats(10, 18, 12, 14, 13, 8);
        Character character2 = new Character("Lyra", 4, "Elf", stats2);
        character2.addClass(new CharacterClass("Rogue", "Assassin", 4));

        Stats stats3 = new Stats(8, 12, 10, 18, 16, 14);
        Character character3 = new Character("Merlin", 7, "Human", stats3);
        character3.addClass(new CharacterClass("Wizard", "School of Evocation", 7));
        characterRepository.save(character1);
        characterRepository.save(character2);
        characterRepository.save(character3);

        System.out.println("Sample characters loaded into the database.");
    }
}

