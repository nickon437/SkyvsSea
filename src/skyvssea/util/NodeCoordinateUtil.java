package skyvssea.util;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Control;

public class NodeCoordinateUtil {

    /**
     * Get X coordinator of a node on the screen.
     *
     * @param node
     * @return X coordinator of a node
     */
    public static double getX(Node node) {
        Bounds screenBounds = node.localToScreen(node.getBoundsInLocal());
        return screenBounds.getMinX();
    }

    /**
     * Get Y coordinator of a node on the screen.
     *
     * @param node
     * @return Y coordinator of a node
     */
    public static double getY(Node node) {
        Bounds screenBounds = node.localToScreen(node.getBoundsInLocal());
        return screenBounds.getMinY();
    }

    /**
     * Get X coordinator of the right of a control on the screen.
     *
     * @param control
     * @return X coordinator to the right of a control
     */
    public static double getRightX(Control control) {
        return getX(control) + control.getWidth() + 10;
    }
}
