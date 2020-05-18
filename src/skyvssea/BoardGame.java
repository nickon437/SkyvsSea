package skyvssea;

import javafx.scene.Scene;
import javafx.stage.Stage;
import skyvssea.controller.Controller;
import skyvssea.view.ActionPane;
import skyvssea.view.BoardPane;
import skyvssea.view.BoardSetupView;
import skyvssea.view.InfoPane;
import skyvssea.view.MainControlPane;
import skyvssea.view.MainView;

public class BoardGame{
	
	public Stage stage = new Stage();
	public BoardGame(BoardSetupView boardSetup) {

		// TODO Auto-generated constructor stub
        Controller controller = new Controller();
        stage.setTitle("Sky vs. Sea");
        int col = Integer.valueOf(boardSetup.getBoardColTextField().getText());
        int row = Integer.valueOf(boardSetup.getBoardRowTextField().getText());
        
        BoardPane boardPane = new BoardPane(controller,col,row);
        ActionPane actionPane = new ActionPane(controller);
        MainControlPane primaryPane = new MainControlPane(boardPane, actionPane);

        InfoPane infoPane = new InfoPane();
        MainView root = new MainView(primaryPane, infoPane);
        
        //Phil - add a new pane like boardSetup
        
        // Nick - There should be a better way to the models and views for controller
        controller.setViewsAndModels(boardSetup,boardPane, actionPane, infoPane);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
