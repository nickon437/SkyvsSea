package skyvssea.controller;

import javafx.stage.Stage;
import skyvssea.BoardGame;
import skyvssea.view.BoardSetupView;

public class BoardSetupController {

    private Stage stage;

    public BoardSetupController(Stage stage) {
        this.stage = stage;
    }

    public void handleConfirmBtn(BoardSetupView boardSetup) {
        String row = boardSetup.getBoardRowTextField().getText();
        String col = boardSetup.getBabyPieceTextField().getText();
        String big = boardSetup.getBigPieceTextField().getText();
        String mid = boardSetup.getMidPieceTextField().getText();
        String small = boardSetup.getSmallPieceTextField().getText();
        String baby = boardSetup.getBabyPieceTextField().getText();

        int rowNumber = Integer.valueOf(row);
        int colNumber = Integer.valueOf(row);
        int bigNumber = Integer.valueOf(big);
        int midNumber = Integer.valueOf(mid);
        int smallNumber = Integer.valueOf(small);
        int babyNumber = Integer.valueOf(baby);

        if (row.isEmpty() && col.isEmpty() && big.isEmpty()
                && mid.isEmpty() && small.isEmpty() && baby.isEmpty()) {
            boardSetup.getTips().setVisible(true);
            return;
        } else if ((colNumber < 4) || (rowNumber < 4)|| (bigNumber < 1)
                || (midNumber < 1) || (smallNumber < 1) || (babyNumber < 1)) {
            boardSetup.getTips().setVisible(true);
            return;
        } else if (rowNumber < (bigNumber + midNumber + smallNumber + babyNumber)) {
            boardSetup.getTips().setVisible(true);
            return;
        } else {
            boardSetup.getTips().setVisible(false);
            BoardGame boardGame = new BoardGame(boardSetup);
            boardGame.stage.show();
            stage.close();
        }
    }

    public void handleClearBtn(BoardSetupView boardSetup) {
        boardSetup.getTips().setVisible(false);
        boardSetup.getBoardColTextField().setText("");
        boardSetup.getBoardRowTextField().setText("");
        boardSetup.getBigPieceTextField().setText("");
        boardSetup.getMidPieceTextField().setText("");
        boardSetup.getSmallPieceTextField().setText("");
        boardSetup.getBabyPieceTextField().setText("");
    }
}
