package skyvssea.model.command;

import skyvssea.model.piece.AbstractPiece;
import skyvssea.model.specialeffect.SpecialEffect;

public class PerformSpecialEffectCommand extends AbstractCommand {

    private AbstractPiece attacker;
    private AbstractPiece target;
    private SpecialEffect specialEffect;

    public PerformSpecialEffectCommand(AbstractPiece attacker, AbstractPiece target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void execute() {
        this.specialEffect = attacker.performSpecialEffect(target);
    }

    @Override
    public void undo() {
        target.removeAppliedSpecialEffect(specialEffect);
        target.setSpecialEffectCounter(0);
    }
}
