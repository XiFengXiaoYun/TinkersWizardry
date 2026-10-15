package com.xifeng.tinkers_wizardry.conarm.upgrade;

public enum UpgradeType {
    POTENCY("Potency"),
    COOLDOWN("Cooldown"),
    COST("Cost");

    public final String type;

    UpgradeType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return type.toLowerCase() + "_upgrade";
    }
    public String getTrueIdentifier() {
        return getIdentifier() + "_armor";
    }
}
