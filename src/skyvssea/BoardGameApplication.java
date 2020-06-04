package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.BoardSetupController;
import skyvssea.database.DatabaseSetup;
import skyvssea.view.BoardSetupView;
import skyvssea.view.LandingPage;

public class BoardGameApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		setupDatabase();
		BoardSetupController boardSetupController = new BoardSetupController(primaryStage);
		BoardSetupView boardSetup = new BoardSetupView(boardSetupController);

		LandingPage landingPage = new LandingPage(boardSetup);

		Scene scene = new Scene(landingPage);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sky vs. Sea");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void setupDatabase() {
		DatabaseSetup.rebuildDatabase();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
