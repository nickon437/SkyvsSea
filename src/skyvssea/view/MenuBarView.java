package skyvssea.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import skyvssea.controller.Controller;

public class MenuBarView {
    private MenuBar menuBar = new MenuBar();
    private Menu fileMenu = new Menu("File");
    private Menu editMenu = new Menu("Edit");
    private Menu aboutMenu = new Menu("Help");
    private MenuItem saveMenuItem = new MenuItem("Save as..");
    private MenuItem loadMenuItem = new MenuItem("Load");
    private MenuItem undoMenuItem = new MenuItem("Undo");
    private MenuItem gameRuleItem = new MenuItem("Game rule");

    public MenuBarView(Controller controller) {
        fileMenu.getItems().addAll(saveMenuItem, loadMenuItem);
        editMenu.getItems().add(undoMenuItem);
        aboutMenu.getItems().add(gameRuleItem);
        menuBar.getMenus().addAll(fileMenu, editMenu, aboutMenu);

//        saveMenuItem.setOnAction(e -> );
//        loadMenuItem.setOnAction(e -> );
//        undoMenuItem.setOnAction(e -> );
//        gameRuleItem.setOnAction(e -> );
    }
}
