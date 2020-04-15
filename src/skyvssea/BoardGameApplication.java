package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.model.Board;
import skyvssea.model.PieceManager;
import skyvssea.model.Player;
import skyvssea.view.*;

public class BoardGameApplication extends Application {

    private Player[] players = new Player[2];
    private Player currentPlayer;
//    List<skyvssea.model.Player> playerList = new ArrayList<>();
//    skyvssea.model.Player currentPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setUpPlayers();


        buildView(primaryStage);
    }

    private void buildView(Stage stage) {
        Controller controller = new Controller();
        stage.setTitle("Sky vs. Sea");

        BoardPane boardPane = new BoardPane(controller);
        Board board = new Board(boardPane);
        ActionPane actionPane = new ActionPane();
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);

        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);

//        Game game = new Game(board);
        PieceManager pieceManager = new PieceManager(board);
        boardPane.setPieceGroup(pieceManager.getAllPieceViews());

        // Nick - There should be a better way to the models and views for controller
        controller.setViewsAndModels(board, pieceManager, boardPane);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void setUpPlayers() {
        players[0] = new Player(Player.BLUE_COLOUR);
        players[1] = new Player(Player.RED_COLOUR);
        currentPlayer = players[0];
    }

    private void nextTurn() {
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];
    }

    public static void main(String[] args) {
        launch(args);
    }
}