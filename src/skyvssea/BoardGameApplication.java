package skyvssea;

import javafx.application.Application;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.model.Player;

public class BoardGameApplication extends Application {

    private Player[] players = new Player[2];
    private Player currentPlayer;
//    List<skyvssea.model.Player> playerList = new ArrayList<>();
//    skyvssea.model.Player currentPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setUpPlayers();

        Controller controller = new Controller();
        controller.buildView(primaryStage);
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
