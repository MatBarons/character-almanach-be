package com.character_almanach.common.character;

import java.util.List;

public interface IHasClassesAndTotalLevel {
    List<? extends GenericClasses> getClasses();
    int getTotalLevel();
}
