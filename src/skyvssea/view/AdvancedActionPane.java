package skyvssea.view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import skyvssea.controller.Controller;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;

public class AdvancedActionPane extends SplitPane {
    private HBox buttonHolder = new HBox();
    private Button paneOpenerBtn = new Button("Advanced options");
    private Button undoBtn = new Button("Undo");
    private Button saveBtn = new Button("Save");
    private Button loadBtn = new Button("Load");
    private Button restartBtn = new Button("Restart");

    private ImageView arrowUpImage = new ImageView("resources/icons/arrow-up.png");
    private ImageView arrowDownImage = new ImageView("resources/icons/arrow-down.png");

    private boolean isCollapsed = true;

    public AdvancedActionPane(Controller controller) {
        this.setOrientation(Orientation.VERTICAL);
        this.getItems().addAll(paneOpenerBtn, buttonHolder);
        buttonHolder.getChildren().addAll(undoBtn, saveBtn, loadBtn, restartBtn);

        formatPaneOpenerBtn(paneOpenerBtn, controller);
        formatButtonHolder(buttonHolder, controller);
        formatUndoButton(undoBtn, controller);
        formatSaveButton(saveBtn, controller);
        formatLoadButton(loadBtn, controller);
        formatRestartButton(restartBtn, controller);

        formatGraphic(arrowUpImage);
        formatGraphic(arrowDownImage);
    }

    private void formatPaneOpenerBtn(Button button, Controller controller) {
        button.setMaxWidth(Double.MAX_VALUE);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        button.setGraphic(arrowUpImage);
        button.setGraphicTextGap(4);
        button.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(button, true));
        button.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(button, false));
        button.setOnMouseClicked(e -> collapseButtonHolder());
    }

    private void formatButtonHolder(HBox holder, Controller controller) {
        holder.setSpacing(ButtonUtil.BUTTON_SPACING);
        double buttonHeight = isCollapsed ? 0 : ButtonUtil.STANDARD_BUTTON_HEIGHT;

        for (Node node : holder.getChildren()) {
            Button button = (Button) node;
            ButtonUtil.formatStandardButton(button, ColorUtil.SECONDARY_BUTTON_COLOR);
            ButtonUtil.maximizeHBoxControlSize(button);
            button.setMinHeight(0);
            button.setPrefHeight(buttonHeight);

            button.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(button, true));
            button.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(button, false));
        }
    }

    private void formatUndoButton(Button button, Controller controller) {
        ButtonUtil.formatGraphic(button, "resources/icons/undo.png");
        button.setOnMouseClicked(e -> controller.handleUndoButton());
    }

    private void formatSaveButton(Button button, Controller controller) {
        ButtonUtil.formatGraphic(button, "resources/icons/save.png");
        // TODO: Add handle click
    }

    private void formatLoadButton(Button button, Controller controller) {
        ButtonUtil.formatGraphic(button, "resources/icons/load.png");
        // TODO: Add handle click
    }

    private void formatRestartButton(Button button, Controller controller) {
        ButtonUtil.formatGraphic(button, "resources/icons/restart.png");
        // TODO: Add handle click
    }

    private void formatGraphic(ImageView imageView) {
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitHeight(15);
    }

    private void collapseButtonHolder() {
        isCollapsed = !isCollapsed;
        double newButtonHeight = isCollapsed ? 0 : ButtonUtil.STANDARD_BUTTON_HEIGHT;
        ImageView newGraphic = isCollapsed ? arrowUpImage : arrowDownImage;

        Timeline timeline = new Timeline();
        Duration animationDuration = Duration.seconds(0.5);

        List<KeyFrame> kfButtonHeights = new ArrayList<>();
        for (Node node : buttonHolder.getChildren()) {
            Button button = (Button) node;
            KeyValue kvButtonHeight = new KeyValue(button.prefHeightProperty(), newButtonHeight, Interpolator.EASE_IN);
            KeyFrame kfButtonHeight = new KeyFrame(animationDuration, kvButtonHeight);
            kfButtonHeights.add(kfButtonHeight);
        }

        KeyValue kvGraphic = new KeyValue(paneOpenerBtn.graphicProperty(), newGraphic, Interpolator.EASE_IN);
        KeyFrame kfGraphic = new KeyFrame(animationDuration, kvGraphic);

        timeline.getKeyFrames().addAll(kfButtonHeights);
        timeline.getKeyFrames().add(kfGraphic);
        timeline.play();
    }
}
