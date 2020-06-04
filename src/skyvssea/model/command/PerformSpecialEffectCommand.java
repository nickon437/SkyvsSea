package skyvssea.model.command;

import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

public class PerformSpecialEffectCommand implements Command {

    private SpecialEffect specialEffect;
    private AbstractPiece caster;
    private AbstractPiece target;
    private HistoryManager historyManager;

    public PerformSpecialEffectCommand(AbstractPiece caster, AbstractPiece target, HistoryManager historyManager) {
        this.specialEffect = SpecialEffectFactory.getInstance().copy(caster.getSpecialEffect());
        this.caster = caster;
        this.target = target;
        this.historyManager = historyManager;
    }

    @Override
    public void execute() {
        if (specialEffect != null && caster.getSpecialEffectCounter() <= 0) {
            target.getSpecialEffectManager().add(specialEffect);

            Command updateCounterCommand = new UpdateCounterCommand(caster, caster.getDefaultSpecialEffectCooldown());
            historyManager.storeAndExecute(updateCounterCommand);
        }
    }

    @Override
    public void undo() {
        target.getSpecialEffectManager().remove(specialEffect);
    }
}
