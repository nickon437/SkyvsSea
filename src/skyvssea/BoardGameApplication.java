package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.model.Board;
import skyvssea.model.PieceManager;
import skyvssea.model.Player;
import skyvssea.model.TurnManager;
import skyvssea.view.*;

public class BoardGameApplication extends Application {

//    private Player[] players = new Player[2];
//    private Player currentPlayer;
//    List<skyvssea.model.Player> playerList = new ArrayList<>();
//    skyvssea.model.Player currentPlayer;


    //new
    TurnManager turnManager = new TurnManager();
    Player currentPlayer = turnManager.currentPlayer;
    Player [] players = turnManager.players;
    @Override
    public void start(Stage primaryStage) throws Exception{
        turnManager.setUpPlayers();


        buildView(primaryStage);
    }

    private void buildView(Stage stage) {
         stage.setTitle("Sky vs. Sea");
        Controller controller = new Controller();

        BoardPane boardPane = new BoardPane(controller);
        Board board = new Board(boardPane.getTileGroup());
        ActionPane actionPane = new ActionPane();
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);

        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);

//        Game game = new Game(board);
        PieceManager pieceManager = new PieceManager(board);
        boardPane.setPieceGroup(pieceManager.getAllPieceViews());

        // Nick - There should be a better way to the models and views for controller
        controller.setViewsAndModels(board, pieceManager, boardPane);

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