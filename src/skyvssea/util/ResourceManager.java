package skyvssea.util;

import javafx.scene.control.Labeled;
import javafx.scene.text.Font;

public class ResourceManager {
    public static final String ROBOTO_BOLD = "file:resources/fonts/Roboto/Roboto-Bold.ttf";
    public static final String ROBOTO_MEDIUM = "file:resources/fonts/Roboto/Roboto-Medium.ttf";
    public static final String ROBOTO_REGULAR = "file:resources/fonts/Roboto/Roboto-Regular.ttf";

    public static final Font TITLE_STYLE = Font.loadFont(ROBOTO_BOLD, 50);
    public static final Font HEADING_STYLE = Font.loadFont(ROBOTO_MEDIUM, 23);
    public static final Font NORMAL_BOLD_STYLE = Font.loadFont(ROBOTO_BOLD, 12);
    public static final Font NORMAL_STYLE = Font.loadFont(ROBOTO_REGULAR, 12);

    public static void setFont(Font font, Labeled... labeleds) {
        for (Labeled labeled : labeleds) {
            labeled.setFont(font);
        }
    }

    public static void setFont(String fontURL, int size, Labeled... labeleds) {
        Font font = Font.loadFont(fontURL, size);
        for (Labeled labeled : labeleds) {
            labeled.setFont(font);
        }
    }
}
