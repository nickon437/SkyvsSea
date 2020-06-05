package skyvssea.model;

public enum SpecialEffectCode {
    NOT_APPLICABLE("Not applicable", ""),
    DOUBLE_ATTACK_RANGE("Double attack range", ""),
    DOUBLE_MOVE_RANGE("Double move range", ""),
    RETARDING("Retarding", ""),
    FREEZING("Freezing", ""),
    WEAKENING("Weakening", ""),
    STRENGTHENING("Strengthening", ""),
    PASSIVE_DEFENCE_BOOST("Passive defence boost", "Increasing Attack Level of Nearby Comrades"),
    PASSIVE_ATTACK_BOOST("Passive attack boost", "Increasing Defence Level of Nearby Comrades"),
    PASSIVE_ANTI_SPECIAL_EFFECT("Passive anti special effect", "Shielding Nearby Comrades from Enemies' Special Effects"),
    PASSIVE_FREEZING("Passive freezing", "Freezing Nearby Enemies"),
    PASSIVE_DEFENCE_BOOST_COST_ATTACK("Passive defence boost cost attack", "Self Defence Level Up, Self Attack Level Down"),
    PASSIVE_ATTACK_BOOST_COST_DEFENCE("Passive attack boost cost defence", "Self Attack Level Up, Self Defence Level Down");

    private String text;
    private String description;

    SpecialEffectCode(String text, String description) {
        this.text = text;
        this.description = description;
    }

    public String getText() {
        return this.text;
    }

    public String getDescription() {
        return this.description;
    }

    public static SpecialEffectCode fromString(String text) {
        for (SpecialEffectCode specialEffectCode : SpecialEffectCode.values()) {
            if (specialEffectCode.text.equalsIgnoreCase(text)) {
                return specialEffectCode;
            }
        }
        return null;
    }
}
