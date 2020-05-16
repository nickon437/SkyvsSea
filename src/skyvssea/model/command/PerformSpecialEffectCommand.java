package skyvssea.model.command;

import skyvssea.model.SpecialEffectFactory;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

public class PerformSpecialEffectCommand implements Command {

    private SpecialEffect specialEffect;
    private AbstractPiece attacker;
    private AbstractPiece target;
    private HistoryManager historyManager;

    public PerformSpecialEffectCommand(AbstractPiece attacker, AbstractPiece target, HistoryManager historyManager) {
        this.specialEffect = SpecialEffectFactory.getInstance().copy(attacker.getSpecialEffect());
        this.attacker = attacker;
        this.target = target;
        this.historyManager = historyManager;
    }

    @Override
    public void execute() {
        if (specialEffect != null && attacker.getSpecialEffectCounter() <= 0) {
            target.getSpecialEffectManagerProxy().add(specialEffect);

            Command updateCounterCommand = new UpdateCounterCommand(attacker, attacker.getDefaultSpecialEffectCooldown());
            historyManager.storeAndExecute(updateCounterCommand);
        }
    }

    @Override
    public void undo() {
        target.getSpecialEffectManagerProxy().remove(specialEffect);
    }
}
