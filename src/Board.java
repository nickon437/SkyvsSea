import java.util.ArrayList;
import java.util.List;

public class Board {
    private int width;
    private int height;
    private List<Eagle> eagleList = new ArrayList<>();
    private List<Shark> sharkList = new ArrayList<>();
//    private List<CellCoordinate> coordinateList = new ArrayList<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void findPossibleMoves(Piece forPiece) {

    }

    private List<CellCoordinate> findOccupiedCells() {
        List<CellCoordinate> occupiedCells = new ArrayList<>();

        return occupiedCells;
    }

    private List<CellCoordinate> validateDestCells() {
        List<CellCoordinate> validatedCells = new ArrayList<>();

        return validatedCells;
    }

    private List<Piece> findPossibleTargets(Piece forPiece) {
        List<Piece> possibleTargets = new ArrayList<>();

        return possibleTargets;
    }

    private void killTarget(Piece target) {

    }

    private void removePiece(Piece target) {

    }

    private void performSpeicalEffect(Piece ofPiece, Piece toPiece) {

    }

}
