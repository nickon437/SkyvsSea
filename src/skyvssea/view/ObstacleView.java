package skyvssea.view;

import javafx.scene.image.ImageView;
import skyvssea.model.Avatar;

public class ObstacleView extends Avatar {

    private static final String volcanoURL = "file:resources/icons/volcano.png";
    private static final String volcanoURL2 = "file:resources/icons/volcano-2.png";
    private ImageView imageView;

    public ObstacleView() {
        imageView = Math.random() < 0.5 ? new ImageView(volcanoURL) : new ImageView(volcanoURL2);
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
    }

    @Override
    public void updateSize(double tileSize) {
        final double CONTENT_PERCENTAGE = 1;
        imageView.setFitHeight(tileSize * CONTENT_PERCENTAGE);
    }
}
