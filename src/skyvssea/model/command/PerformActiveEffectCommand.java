package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffectObject;

public class PerformActiveEffectCommand implements Command {

    private SpecialEffectObject activeEffect;
    private AbstractPiece caster;
    private AbstractPiece target;
    private HistoryManager historyManager;

    public PerformActiveEffectCommand(AbstractPiece caster, AbstractPiece target, HistoryManager historyManager) {
        this.activeEffect = (SpecialEffectObject) caster.getActiveEffect().copy();
        this.caster = caster;
        this.target = target;
        this.historyManager = historyManager;
    }

    @Override
    public void execute() {
        if (activeEffect != null && caster.getActiveEffectCounter() <= 0) {
            target.getSpecialEffectManagerProxy().add(activeEffect);

            Command updateCounterCommand = new UpdateCounterCommand(caster, caster.getActiveEffectCoolDown());
            historyManager.storeAndExecute(updateCounterCommand);
        }
    }

    @Override
    public void undo() {
        target.getSpecialEffectManagerProxy().remove(activeEffect);
    }
}
