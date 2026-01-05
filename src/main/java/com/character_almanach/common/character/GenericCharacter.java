package com.character_almanach.common.character;

import java.util.List;

public abstract class GenericCharacter<T extends GenericClasses> implements IHasClassesAndTotalLevel {

    public abstract List<T> getClasses();
    public abstract int getTotalLevel();
}
