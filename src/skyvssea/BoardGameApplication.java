package skyvssea;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.model.Hierarchy;
import skyvssea.view.*;

public class BoardGameApplication extends Application {
	
	private Map<Hierarchy, Integer> lineup;
	
	private void createInitialLineUp() {
		lineup = new HashMap<>();
		lineup.put(Hierarchy.BIG, 1);
		lineup.put(Hierarchy.MEDIUM, 1);
		lineup.put(Hierarchy.SMALL, 1);
		lineup.put(Hierarchy.BABY, 1);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	createInitialLineUp();
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
        controller.setViewsAndModels(boardPane, actionPane, infoPane, lineup);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}