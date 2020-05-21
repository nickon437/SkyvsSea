package skyvssea.util;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationUtil {
    public static KeyFrame formatKeyFrame(WritableValue target, double endValue, Duration duration) {
        KeyValue keyValue = new KeyValue(target, endValue, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);
        return keyFrame;
    }

    public static void fadeInTransition(Node node) {
        node.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public static void fadeOutTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> node.setVisible(false));
        fadeTransition.play();
    }
}
