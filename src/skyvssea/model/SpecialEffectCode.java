package skyvssea.model;

public enum SpecialEffectCode {
    NOT_APPLICABLE("Not applicable"),
    DOUBLE_ATTACK_RANGE("Double attack range"),
    DOUBLE_MOVE_RANGE("Double move range"),
    RETARDING("Retarding"),
    FREEZING("Freezing"),
    WEAKENING("Weakening"),
    STRENGTHENING("Strengthening");

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
