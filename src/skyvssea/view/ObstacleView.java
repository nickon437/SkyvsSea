package skyvssea.view;

import javafx.scene.image.ImageView;
import skyvssea.model.Avatar;

public class ObstacleView extends Avatar {

    private static final String volcanoURL = "resources/icons/volcano.png";
    private ImageView imageView = new ImageView(volcanoURL);

    public ObstacleView() {
        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);
    }

    @Override
    public void updateSize(double tileSize) {
        final double CONTENT_PERCENTAGE = 1;
        imageView.setFitHeight(tileSize * CONTENT_PERCENTAGE);
    }
}
