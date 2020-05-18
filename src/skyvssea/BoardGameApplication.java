package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.BoardSetupController;
import skyvssea.view.BoardSetupView;

public class BoardGameApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		BoardSetupController controller = new BoardSetupController(primaryStage);
		BoardSetupView boardSetup = new BoardSetupView(controller);

		Scene scene = new Scene(boardSetup);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Design Your Game");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
