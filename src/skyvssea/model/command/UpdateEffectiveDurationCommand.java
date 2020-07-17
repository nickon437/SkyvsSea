package skyvssea.model.command;

import skyvssea.model.SpecialEffectManagerInterface;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class UpdateEffectiveDurationCommand implements Command {

    private SpecialEffectManagerInterface specialEffectManager;
    private SpecialEffectObject specialEffect;
    private int currentEffectiveDuration;
    private int newEffectiveDuration;

    public UpdateEffectiveDurationCommand(SpecialEffectManagerInterface specialEffectManager, SpecialEffectObject specialEffect,
                                          int newEffectiveDuration) {
        this.specialEffectManager = specialEffectManager;
        this.specialEffect = specialEffect;
        this.currentEffectiveDuration = specialEffect.getEffectiveDuration();
        this.newEffectiveDuration = newEffectiveDuration;
    }

    @Override
    public void execute() {
        specialEffect.setEffectiveDuration(newEffectiveDuration);
        if (newEffectiveDuration == 0) {
            specialEffectManager.remove(specialEffect);
        }
    }

    @Override
    public void undo() {
        if (specialEffect.getEffectiveDuration() == 0) {
            specialEffectManager.add(specialEffect);
        }
        specialEffect.setEffectiveDuration(currentEffectiveDuration);
    }
}
