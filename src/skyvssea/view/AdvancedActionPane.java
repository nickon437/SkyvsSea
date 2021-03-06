package skyvssea.view;

import com.google.java.contract.Requires;
import javafx.animation.*;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import skyvssea.controller.Controller;
import skyvssea.util.AnimationUtil;
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

    private ImageView arrowUpImage = new ImageView("file:resources/icons/arrow-up.png");
    private ImageView arrowDownImage = new ImageView("file:resources/icons/arrow-down.png");

    private boolean isCollapsed = true;

    @Requires("controller != null")
    public AdvancedActionPane(Controller controller) {
        this.setOrientation(Orientation.VERTICAL);
        this.getItems().addAll(paneOpenerBtn, buttonHolder);
        buttonHolder.getChildren().addAll(undoBtn, saveBtn, loadBtn);

        formatPaneOpenerBtn(paneOpenerBtn);
        formatButtonHolder(buttonHolder);
        formatUndoButton(undoBtn, controller);
        formatSaveButton(saveBtn, controller);
        formatLoadButton(loadBtn, controller);

        formatGraphic(arrowUpImage);
        formatGraphic(arrowDownImage);
    }

    @Requires("button != null")
    private void formatPaneOpenerBtn(Button button) {
        button.setMaxWidth(Double.MAX_VALUE);
        ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
        button.setGraphic(arrowUpImage);
        button.setGraphicTextGap(4);
        ButtonUtil.formatHoveringEffect(button);
        button.setOnMouseClicked(e -> collapseButtonHolder());
    }

    @Requires("holder != null")
    private void formatButtonHolder(HBox holder) {
        holder.setSpacing(ButtonUtil.BUTTON_SPACING);
        double buttonHeight = isCollapsed ? 0 : ButtonUtil.STANDARD_BUTTON_HEIGHT;

        for (Node node : holder.getChildren()) {
            Button button = (Button) node;
            ButtonUtil.formatStandardButton(button, ColorUtil.SECONDARY_BUTTON_COLOR);
            ButtonUtil.maximizeHBoxControlSize(button);
            button.setMinHeight(0);
            button.setPrefHeight(buttonHeight);

            ButtonUtil.formatHoveringEffect(button);
        }
    }

    @Requires("button != null && controller != null")
    private void formatUndoButton(Button button, Controller controller) {
        ButtonUtil.formatGraphic(button, "file:resources/icons/undo.png");
        button.setOnMouseClicked(e -> controller.handleUndoButton());
    }

    @Requires("button != null && controller != null")
    private void formatSaveButton(Button button, Controller controller) {
        ButtonUtil.formatGraphic(button, "file:resources/icons/save.png");
        button.setOnAction(e -> controller.handleSaveButton());
    }

    @Requires("button != null && controller != null")
    private void formatLoadButton(Button button, Controller controller) {
        ButtonUtil.formatGraphic(button, "file:resources/icons/load.png");
        button.setOnAction(e -> controller.handleLoadButton());
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
            KeyFrame kfButtonHeight = AnimationUtil.formatKeyFrame(button.prefHeightProperty(), newButtonHeight, animationDuration);
            kfButtonHeights.add(kfButtonHeight);
        }

        timeline.getKeyFrames().addAll(kfButtonHeights);
        timeline.play();

        paneOpenerBtn.setGraphic(newGraphic);
    }

    public void setUndoBtnDisable(boolean isDisabled) {
        undoBtn.setDisable(isDisabled);
    }
}
