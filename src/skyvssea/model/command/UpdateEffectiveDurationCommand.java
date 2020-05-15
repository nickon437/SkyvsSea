package skyvssea.model.command;

import skyvssea.model.SpecialEffectManagerInterface;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

public class UpdateEffectiveDurationCommand extends AbstractCommand {

    private AbstractPiece target;
    private SpecialEffectManagerInterface specialEffectManager;
    private SpecialEffect specialEffect;
    private Class specialEffectClass;
    private int currentEffectiveDuration;
    private int newEffectiveDuration;

    public UpdateEffectiveDurationCommand(AbstractPiece target, SpecialEffectManagerInterface specialEffectManager,
                                          SpecialEffect specialEffect, int newEffectiveDuration) {
        this.target = target;
        this.specialEffectManager = specialEffectManager;
        this.specialEffect = specialEffect;
        this.specialEffectClass = specialEffect.getClass();
        this.currentEffectiveDuration = specialEffect.getEffectiveDuration();
        this.newEffectiveDuration = newEffectiveDuration;
    }

    @Override
    public void execute() {
//        int newEffectiveDuration = specialEffect.getEffectiveDuration() - 1;
//        specialEffect.setEffectiveDuration(newEffectiveDuration);
//        if (newEffectiveDuration <= 0) {
//            specialEffectManager.remove(specialEffect);
//        }

        specialEffect.setEffectiveDuration(newEffectiveDuration);
        if (newEffectiveDuration <= 0) {
            specialEffectManager.remove(specialEffect);
        }
    }

    @Override
    public void undo() {
        if (specialEffect.getEffectiveDuration() <= 0) {
            specialEffectManager.add(specialEffect);
        }
        specialEffect.setEffectiveDuration(currentEffectiveDuration);
    }
}
