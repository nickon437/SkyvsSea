package skyvssea.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.view.*;

public class BoardSetupController {

    private Stage stage;

    public BoardSetupController(Stage stage) {
        this.stage = stage;
    }

    public void handleConfirmBtn(BoardSetupView boardSetup) {
        stage.close();

        Controller controller = new Controller();
        stage.setTitle("Sky vs. Sea");
        int row = boardSetup.getBoardSize()[0];
        int col = boardSetup.getBoardSize()[1];

        BoardPane boardPane = new BoardPane(controller, col, row);
        ActionPane actionPane = new ActionPane(controller);
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);

        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);

        controller.setViewsAndModels(boardSetup, boardPane, actionPane, infoPane);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
