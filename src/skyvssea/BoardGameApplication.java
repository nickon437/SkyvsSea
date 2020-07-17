package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.BoardSetupController;
import skyvssea.controller.LandingPageController;
import skyvssea.database.DatabaseSetup;
import skyvssea.view.BoardSetupView;
import skyvssea.view.LandingPage;

public class BoardGameApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		DatabaseSetup.setupDatabase();

		BoardSetupController boardSetupController = new BoardSetupController(primaryStage);
		BoardSetupView boardSetup = new BoardSetupView(boardSetupController);

		LandingPageController landingPageController = new LandingPageController(primaryStage);
		LandingPage landingPage = new LandingPage(landingPageController, boardSetup);

		Scene scene = new Scene(landingPage);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sky vs. Sea");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
