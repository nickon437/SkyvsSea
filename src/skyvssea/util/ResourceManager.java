package skyvssea.util;

import javafx.scene.control.Labeled;
import javafx.scene.text.Font;

public class ResourceManager {
    public static final Font TITLE_FONT = Font.loadFont("file:resources/fonts/Roboto/Roboto-Bold.ttf", 50);
    public static final Font HEADING_FONT = Font.loadFont("file:resources/fonts/Roboto/Roboto-Medium.ttf", 23);
    public static final Font NORMAL_BOLD_FONT = Font.loadFont("file:resources/fonts/Roboto/Roboto-Bold.ttf", 12);
    public static final Font NORMAL_FONT = Font.loadFont("file:resources/fonts/Roboto/Roboto-Regular.ttf", 12);

    public static void setFont(Font font, Labeled... labeleds) {
        for (Labeled labeled : labeleds) {
            labeled.setFont(font);
        }
    }
}
