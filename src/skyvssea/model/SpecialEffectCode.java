package skyvssea.model;

public enum SpecialEffectCode {
    NOT_APPLICABLE("Not applicable"),
    DOUBLE_ATTACK_RANGE("Double attack range"),
    DOUBLE_MOVE_RANGE("Double move range"),
    RETARDING("Retarding"),
    FREEZING("Freezing"),
    WEAKENING("Weakening"),
    STRENGTHENING("Strengthening"),
    PASSIVE_DEFENCE_BOOST("Passive defence boost"),
    PASSIVE_ATTACK_BOOST("Passive attack boost"),
    PASSIVE_ANTI_SPECIAL_EFFECT("Passive anti special effect"),
    PASSIVE_FREEZING("Passive freezing"),
    PASSIVE_DEFENCE_BOOST_COST_ATTACK("Passive defence boost cost attack"),
    PASSIVE_ATTACK_BOOST_COST_DEFENCE("Passive attack boost cost defence");

    private String text;

    SpecialEffectCode(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
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
