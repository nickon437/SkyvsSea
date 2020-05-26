package skyvssea.util;

import com.google.java.contract.Requires;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RegionUtil {
    protected static CornerRadii getCornerRadii(Region region) {
        try {
            return region.getBackground().getFills().get(0).getRadii();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static void setCornerRadii(Region region, CornerRadii cornerRadii) {
        try {
            BackgroundFill backgroundFill = region.getBackground().getFills().get(0);
            backgroundFill = new BackgroundFill(backgroundFill.getFill(), cornerRadii, backgroundFill.getInsets());
            region.setBackground(new Background(backgroundFill));
        } catch (NullPointerException e) {
            setBackground(region, null, cornerRadii, null);
        }
    }

    public static Paint getFill(Region region) {
        try {
            return region.getBackground().getFills().get(0).getFill();
        } catch (Exception e) {
            return null;
        }
    }

    public static void setFill(Region region, Paint paint) {
        try {
            BackgroundFill backgroundFill = region.getBackground().getFills().get(0);
            backgroundFill = new BackgroundFill(paint, backgroundFill.getRadii(), backgroundFill.getInsets());
            region.setBackground(new Background(backgroundFill));
        } catch (NullPointerException e) {
            setBackground(region, paint, null, null);
        }
    }

    public static void setBorderRadius(Region region, CornerRadii cornerRadii) {
        try {
            BorderStroke borderStroke = region.getBorder().getStrokes().get(0);
            borderStroke = new BorderStroke(borderStroke.getBottomStroke(), borderStroke.getBottomStyle(), cornerRadii, borderStroke.getWidths());
            region.setBorder(new Border(borderStroke));
        } catch (NullPointerException e) {
            region.setBorder(new Border(new BorderStroke(Color.SLATEGREY, BorderStrokeStyle.SOLID, cornerRadii, new BorderWidths(0.5))));
        }
    }

    public static void setBackground(Region region, Paint paint, CornerRadii cornerRadii, Insets insets) {
        region.setBackground(new Background(new BackgroundFill(paint, cornerRadii, insets)));
    }

    @Requires("node != null")
    public static void setAnchor(Node node, Double top, Double left, Double bottom, Double right) {
        if (top != null) AnchorPane.setTopAnchor(node, top);
        if (left != null) AnchorPane.setLeftAnchor(node, left);
        if (bottom != null) AnchorPane.setBottomAnchor(node, bottom);
        if (right != null) AnchorPane.setRightAnchor(node, right);
    }
}
