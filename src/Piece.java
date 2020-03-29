abstract class Piece {
    private int hierarchyLev;
    private CellCoordinate coord;
    private int numMove;
    private int attRange;
    private String speEffName;
    private String speEffDesc;
    private int speEffCooldown;

    private void movePiece(CellCoordinate destCell) {

    }

    private void getKilled() {

    }

    private void highlightPiece(boolean isHighlighted) {

    }

    abstract protected void performSpeEff(Piece target);
}
