import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game extends Application {

    List<Player> playerList = new ArrayList<>();
    Player currentPlayer;
    Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Player player1 = new Player(Player.BLUE_COLOUR);
        Player player2 = new Player(Player.RED_COLOUR);
        playerList.add(player1);
        playerList.add(player2);
        currentPlayer = player1;

        controller = new Controller();
        controller.buildView(primaryStage);
    }

    private void nextTurn() {
        currentPlayer = currentPlayer.equals(playerList.get(0)) ? playerList.get(1) : playerList.get(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
