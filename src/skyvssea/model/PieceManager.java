package skyvssea.model;

import java.util.ArrayList;
import java.util.Map;

public class PieceManager {
    //Need review
    // Reason for using a hashmap to store the pieces: easier to assign the pieces to initial tile locations
    private Map<Hierarchy, ArrayList<Piece>> sharkPieces;
    private Map<Hierarchy, ArrayList<Piece>> eaglePieces;

    public PieceManager(Board board) {
        initializePieces();
        board.setPiecesOnTiles(sharkPieces, eaglePieces);
    }

    /**
     * Create initial lineup of pieces for both sides
     */
    public void initializePieces() {
        //Using singleton pattern to create the factories (not sure if it's appropriate though)
        PieceFactory sharkFactory = SharkFactory.getInstance();
        PieceFactory eagleFactory = EagleFactory.getInstance();
        sharkPieces = sharkFactory.createInitialLineUp();
        eaglePieces = eagleFactory.createInitialLineUp();
    }
}
