package com.character_almanach.common.utils;

import java.util.List;

import com.character_almanach.dto.get.CharacterClassDto;

public class CharacterUtils {
    public static boolean checkDuplicateClasses(List<CharacterClassDto> classes) {
        return classes.stream().map(c -> c.getClassName()).toList().size() != classes.stream().map(c -> c.getClassName()).distinct().toList().size();
    }
}
