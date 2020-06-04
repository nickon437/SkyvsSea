package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import skyvssea.model.GameState;
import skyvssea.model.Player;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.util.ColorUtil;
import skyvssea.util.RegionUtil;
import skyvssea.util.ResourceManager;

import java.util.*;

public class InfoPane extends VBox {
    private Label playerNameText = new Label("Player's name");
    private VBox playerOverviewPane = new VBox();
    private HBox instructionPane = new HBox();
    private TextArea statusText = new TextArea();
    private TextArea specialEffectText = new TextArea();

    private ImageView rightArrowImg1;
    private ImageView rightArrowImg2;
    private ImageView rightArrowImg3;
    private Label passiveEffectLabel = new Label("Passive effect\n(optional)");
    private Label moveLabel = new Label("Move");
    private Label attackLabel = new Label("Attack\n(Kill or Special Effect)");
    private Label endLabel = new Label("End");
    private List<Node> fullInstructionList = new ArrayList<>();

    private GameState gameState = GameState.READY_TO_MOVE;

    private static final int INSTRUCTION_PANE_THRESHOLD = 335;

    public InfoPane() {
        this.setAlignment(Pos.CENTER);

        formatPlayerOverviewPane();
        formatTextArea(statusText);
        formatTextArea(specialEffectText);

        this.getChildren().addAll(playerOverviewPane, statusText, specialEffectText);
        this.setPadding(new Insets(20d));
        this.setSpacing(20);
    }

    private void formatPlayerOverviewPane() {
        playerOverviewPane.getChildren().addAll(playerNameText, new Separator(), instructionPane);
        playerOverviewPane.setSpacing(10);
        playerOverviewPane.setAlignment(Pos.CENTER);
        formatPlayerNameText();
        formatInstructionPane();
    }

    private void formatPlayerNameText() {
        VBox.setMargin(playerNameText, new Insets(10, 20, 0, 20));
        ResourceManager.setFont(ResourceManager.ROBOTO_BOLD, 15, playerNameText);
    }

    private void formatInstructionPane() {
        // Icons
        Image image = new Image("file:resources/icons/chevron-right.png");
        rightArrowImg1 = generateFormattedImgView(image);
        rightArrowImg2 = generateFormattedImgView(image);
        rightArrowImg3 = generateFormattedImgView(image);

        // Labels
        passiveEffectLabel.setTextAlignment(TextAlignment.CENTER);
        passiveEffectLabel.setMinWidth(USE_COMPUTED_SIZE);
        moveLabel.setTextAlignment(TextAlignment.CENTER);
        attackLabel.setTextAlignment(TextAlignment.CENTER);
        endLabel.setTextAlignment(TextAlignment.CENTER);

        // Content
        fullInstructionList = Arrays.asList(passiveEffectLabel, rightArrowImg1, moveLabel, rightArrowImg2, attackLabel,
                rightArrowImg3, endLabel);

        // Instruction pane's properties
        instructionPane.setAlignment(Pos.CENTER);
        instructionPane.widthProperty().addListener(((observable, oldValue, newValue) -> displayInstruction(oldValue, newValue)));
        HBox.setHgrow(instructionPane, Priority.ALWAYS);
        VBox.setMargin(instructionPane, new Insets(0, 20, 10, 20));
    }

    private ImageView generateFormattedImgView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(20);
        return imageView;
    }

    private void displayInstruction(Number oldWidth, Number newWidth) {
        if (((double) oldWidth == 0 || (double) oldWidth < INSTRUCTION_PANE_THRESHOLD) && (double) newWidth >= INSTRUCTION_PANE_THRESHOLD) {
            instructionPane.getChildren().clear();
            instructionPane.getChildren().addAll(fullInstructionList);
        } else if (((double) oldWidth == 0 || (double) oldWidth >= INSTRUCTION_PANE_THRESHOLD) && (double) newWidth < INSTRUCTION_PANE_THRESHOLD) {
            instructionPane.getChildren().clear();
            if (gameState == GameState.READY_TO_MOVE) {
                instructionPane.getChildren().addAll(passiveEffectLabel, rightArrowImg1, moveLabel);
            }  else if (gameState == GameState.READY_TO_ATTACK || gameState == GameState.KILLING || gameState == GameState.PERFORMING_ACTIVE_EFFECT) {
                instructionPane.getChildren().add(attackLabel);
            }
        }
    }

    public void setCurrentGameState(GameState gameState) {
        this.gameState = gameState;

        ResourceManager.setFont(ResourceManager.ROBOTO_REGULAR, 10, passiveEffectLabel, moveLabel, attackLabel, endLabel);

        if (gameState == GameState.READY_TO_MOVE) {
            ResourceManager.setFont(ResourceManager.NORMAL_BOLD_STYLE, passiveEffectLabel, moveLabel);
        } else if (gameState == GameState.READY_TO_ATTACK || gameState == GameState.KILLING || gameState == GameState.PERFORMING_ACTIVE_EFFECT) {
            ResourceManager.setFont(ResourceManager.NORMAL_BOLD_STYLE, attackLabel);
        }

        displayInstruction(0.0, instructionPane.getWidth());
    }

    private void formatTextArea(TextArea textArea) {
        setTextAreaDisabled(textArea, true);
        VBox.setVgrow(textArea, Priority.ALWAYS);
    }

    private void setTextAreaDisabled(TextArea textArea, boolean isDisabled) {
        textArea.setEditable(!isDisabled);
        textArea.setFocusTraversable(!isDisabled);
        textArea.setMouseTransparent(isDisabled);
    }

    public void setPlayerInfo(Player player) {
        playerNameText.setText(player.getName() + "'s turn");
        Color playerColor = player.getColor();
        RegionUtil.setBackground(playerOverviewPane, playerColor, new CornerRadii(20), null);
        playerNameText.setTextFill(ColorUtil.getTextContrastColor(playerColor));
        passiveEffectLabel.setTextFill(ColorUtil.getTextContrastColor(playerColor));
        moveLabel.setTextFill(ColorUtil.getTextContrastColor(playerColor));
        attackLabel.setTextFill(ColorUtil.getTextContrastColor(playerColor));
        endLabel.setTextFill(ColorUtil.getTextContrastColor(playerColor));
    }

    public void setPieceInfo(AbstractPiece piece) {
        statusText.setText(piece.toString());
        specialEffectText.setText(piece.getSpecialEffectSummary());
    }
}
