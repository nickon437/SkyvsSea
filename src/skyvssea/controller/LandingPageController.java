package skyvssea.controller;

import javafx.stage.Stage;
import skyvssea.database.LoadHandler;

public class LandingPageController {

    private Stage stage;

    public LandingPageController(Stage stage) {
        this.stage = stage;
    }

    public void handleLoadBtn() {
        LoadHandler loadHandler = new LoadHandler();
        loadHandler.loadGame(stage);
    }
}
