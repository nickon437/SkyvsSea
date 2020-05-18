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
        if (boardSetup.isInputValid()){
            BoardGame boardGame = new BoardGame(boardSetup);
            boardGame.stage.show();
            stage.close();
        }
    }
}
