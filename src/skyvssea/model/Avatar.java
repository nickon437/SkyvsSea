package skyvssea.model;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

//public interface Avatar {
//    void updateSize(double tileSize);
//}
public abstract class Avatar extends StackPane { // Nick - Use abstract instead of interface because TileView need to stack up new child which only receive Node.
    public abstract void updateSize(double tileSize);
}