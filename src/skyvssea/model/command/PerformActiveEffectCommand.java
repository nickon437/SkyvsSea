package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class PerformActiveEffectCommand implements Command {

    private SpecialEffectObject activeEffect;
    private AbstractPiece caster;
    private AbstractPiece target;
    private Command updateCounterCommand;

    public PerformActiveEffectCommand(AbstractPiece caster, AbstractPiece target) {
        this.activeEffect = (SpecialEffectObject) caster.getActiveEffect().copy();
        this.caster = caster;
        this.target = target;
    }

    @Override
    public void execute() {
        if (activeEffect != null && caster.getActiveEffectCounter() <= 0) {
            target.getSpecialEffectManager().add(activeEffect);
            updateCounterCommand = new UpdateCounterCommand(caster, caster.getActiveEffectCoolDown());
            updateCounterCommand.execute();
        }
    }

    @Override
    public void undo() {
        target.getSpecialEffectManager().remove(activeEffect);
        updateCounterCommand.undo();
    }
}
