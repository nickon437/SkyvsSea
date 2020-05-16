package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.view.*;

public class BoardGameApplication extends Application {
	ChangeBoardSizePane changeBoardSizePane;
	Controller controller;

	public BoardGameApplication() {
		this.controller = new Controller();
		this.changeBoardSizePane = new ChangeBoardSizePane(controller);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(changeBoardSizePane.gridPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle(" Design Your Game ");
		primaryStage.setHeight(400);
		primaryStage.setWidth(400);
		primaryStage.setResizable(false);
		primaryStage.show();
		changeBoardSizePane.getConfirmButton().setOnAction(e -> controller.handleConfirmBtn(changeBoardSizePane,primaryStage));
	}
	
	

    public static void main(String[] args) {
        launch(args);
    }
}

