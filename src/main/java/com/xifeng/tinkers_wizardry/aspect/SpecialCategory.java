package com.xifeng.tinkers_wizardry.aspect;

import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tinkering.Category;

public class SpecialCategory extends Category {
    public static final Category Wizardry = new SpecialCategory("wizardry");
    public static final ModifierAspect.CategoryAspect aspect = new ModifierAspect.CategoryAspect(Wizardry);
    public final String name;
    public SpecialCategory(String name) {
        super(name);
        this.name = name;
    }
}
