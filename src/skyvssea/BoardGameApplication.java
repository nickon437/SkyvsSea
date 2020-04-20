package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.model.Player;
import skyvssea.view.*;
import skyvssea.model.TurnManager;

public class BoardGameApplication extends Application {

    //new phil TODO: add turnManager
    TurnManager turnManager = new TurnManager();

    @Override
    public void start(Stage primaryStage) throws Exception{
        turnManager.setUpPlayers();

        buildView(primaryStage);
    }

    private void buildView(Stage stage) {
        Controller controller = new Controller();
        stage.setTitle("Sky vs. Sea");

        BoardPane boardPane = new BoardPane(controller);
//        Board board = new Board();//(boardPane.getTileGroup());
        ActionPane actionPane = new ActionPane();
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);

        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);

//        Game game = new Game(board);
//        PieceManager pieceManager = new PieceManager();
//        boardPane.setPieceGroup(pieceManager.getAllPieceViews());

        // Nick - There should be a better way to the models and views for controller
        controller.setViewsAndModels(boardPane);

        //Phil - to add actionpane with change player
        controller.setAction(actionPane);
        controller.setTurnManager(turnManager);
        controller.addActionHandler();

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}