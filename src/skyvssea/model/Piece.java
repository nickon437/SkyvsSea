package skyvssea.model;

import skyvssea.view.PieceView;

import java.util.List;

public abstract class Piece {
    protected String name;
    private Hierarchy level;
    private int numMove;
    private int attackRange;
    private int specialEffectCounter;
    private SpecialEffect specialEffect;
    private List<SpecialEffect> appliedSpecialEffect;
    private PieceView pieceView;

    protected Piece(String name, Hierarchy level, int numMove, int attackRange, int specialEffectCounter, SpecialEffect specialEffect) {
    	this.name = name;
        this.level = level;
    	this.numMove = numMove;
    	this.attackRange = attackRange;
    	this.specialEffectCounter = specialEffectCounter;
    	this.specialEffect = specialEffect;
    	this.pieceView = new PieceView();
    }

    public String getName() { return name; }
    
    abstract protected void performSpeEff(Piece target);
    //Idea: Use prototype creation pattern to create a clone of self specialEffect and pass it to target

    protected SpecialEffect getSpecialEffect() {
		return specialEffect;
	}

	public PieceView getPieceView() {
        return pieceView;
    }
}
