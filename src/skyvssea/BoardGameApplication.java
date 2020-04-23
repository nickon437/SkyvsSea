package skyvssea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.view.*;

public class BoardGameApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        buildView(primaryStage);
    }

    private void buildView(Stage stage) {
        Controller controller = new Controller();
        stage.setTitle("Sky vs. Sea");

        BoardPane boardPane = new BoardPane(controller);
        ActionPane actionPane = new ActionPane(controller);
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);

        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);

        // Nick - There should be a better way to the models and views for controller
        controller.setViewsAndModels(boardPane, actionPane, infoPane);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}