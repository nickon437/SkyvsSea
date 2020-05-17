package skyvssea.util;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.util.Duration;

public class AnimationUtil {
    public static KeyFrame formatKeyFrame(WritableValue target, double endValue, Duration duration) {
        KeyValue keyValue = new KeyValue(target, endValue, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);
        return keyFrame;
    }

    public static KeyFrame formatKeyFrame(WritableValue target, Background background, Duration duration) {
        KeyValue keyValue = new KeyValue(target, background, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);
        return keyFrame;
    }

//    public static formatFadeInTranstion(FadeTransition fadeTransition, Duration duration, Node node) {
//        fadeTransition
//    }
}
