package skyvssea.model;

import java.util.List;

abstract class Piece {
    // TODO: Require constructor to enable these unassigned constant
//    private final int DEFAULT_NUM_MOVE;
//    private final int DEFAULT_ATTACK_RANGE;
//    private final int SPECIAL_EFFECT_COOLDOWN;

    private int hierarchyLev;
    private int numMove;
    private int attackRange;
    private int specialEffectCounter;
    private List<SpecialEffect> appliedSpecialEffect;
//    private SpecialEffect specialEffect

    abstract protected void performSpeEff(Piece target);
}
