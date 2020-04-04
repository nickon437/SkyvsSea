package skyvssea.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import skyvssea.controller.Controller;

public class BoardPane extends Pane {

    Group tileGroup = new Group();

    public BoardPane(double tileSize, Controller controller) {
        for (int y = 0; y < Controller.NUM_SIDE_CELL; y++) {
            for (int x = 0; x < Controller.NUM_SIDE_CELL; x++) {
                TilePane tileView = new TilePane(x, y, tileSize);
                controller.setTile(tileView, x, y);

                tileGroup.getChildren().add(tileView);
                // TODO: Figure out if there is better way to consolidate the tileView and tileModel without breaking MVC.
            }
        }

        this.getChildren().add(tileGroup);
    }

    public void updateTilesSize(double tileSize, double width, double height) {
        double mostLeftX = (width - (tileSize * Controller.NUM_SIDE_CELL)) / 2;
        double mostTopY = (height - (tileSize * Controller.NUM_SIDE_CELL)) / 2;

        for (Node node : tileGroup.getChildren()) {
            TilePane tilePane = (TilePane) node;
            tilePane.updateTileSize(tileSize, mostLeftX, mostTopY);
        }
    }
}
