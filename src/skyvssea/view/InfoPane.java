package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import skyvssea.model.Player;
import skyvssea.model.piece.AbstractPiece;
import skyvssea.util.ColorUtil;
import skyvssea.util.RegionUtil;
import skyvssea.util.ResourceManager;

public class InfoPane extends VBox {
    private Label playerNameText = new Label("Player's name");
    private VBox playerOverviewPane = new VBox();
    private HBox instructionPane = new HBox();
    private TextArea statusText = new TextArea();
    private TextArea specialEffectText = new TextArea();

    private Label passiveEffectLabel = new Label("Passive effect\n(optional)");
    private Label moveLabel = new Label("Move");
    private Label attackLabel = new Label("Attack\n(Kill or Special Effect)");
    private Label endLabel = new Label("End");

    public InfoPane() {
        this.setAlignment(Pos.CENTER);

        formatPlayerOverviewPane();
        setTextAreaDisabled(statusText, true);
        setTextAreaDisabled(specialEffectText, true);

        this.getChildren().addAll(playerOverviewPane, statusText, specialEffectText);

        VBox.setVgrow(statusText, Priority.ALWAYS);
        VBox.setVgrow(specialEffectText, Priority.ALWAYS);
        this.setPadding(new Insets(20d));
        this.setSpacing(20);
    }

    private void formatPlayerOverviewPane() {
        playerOverviewPane.getChildren().addAll(playerNameText, new Separator(), instructionPane);
//        VBox.setMargin(playerOverviewPane, new Insets(20));
//        playerOverviewPane.setPadding(new Insets(20));
        playerOverviewPane.setSpacing(10);
        playerOverviewPane.setAlignment(Pos.CENTER);
        formatPlayerNameText();
        formatInstructionPane();
    }

    private void formatPlayerNameText() {
        VBox.setMargin(playerNameText, new Insets(10, 20, 0, 20));
//        playerNameText.setStyle("-fx-font-weight: bold;");
        ResourceManager.setFont(ResourceManager.NORMAL_BOLD_FONT, playerNameText);
    }

    private void formatInstructionPane() {
        Image image = new Image("file:resources/icons/chevron-right.png");
        ImageView rightArrowImg1 = generateFormattedImgView(image);
        ImageView rightArrowImg2 = generateFormattedImgView(image);
        ImageView rightArrowImg3 = generateFormattedImgView(image);

        passiveEffectLabel.setTextAlignment(TextAlignment.CENTER);
        passiveEffectLabel.setMinWidth(USE_COMPUTED_SIZE);
//        passiveEffectLabel.setWrapText(false);
        moveLabel.setTextAlignment(TextAlignment.CENTER);
        attackLabel.setTextAlignment(TextAlignment.CENTER);
        endLabel.setTextAlignment(TextAlignment.CENTER);

        instructionPane.getChildren().addAll(passiveEffectLabel, rightArrowImg1, moveLabel, rightArrowImg2, attackLabel,
                rightArrowImg3, endLabel);
        instructionPane.setAlignment(Pos.CENTER);
        instructionPane.setMinWidth(335);
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

    private void setTextAreaDisabled(TextArea textArea, boolean isDisabled) {
        textArea.setEditable(!isDisabled);
        textArea.setFocusTraversable(!isDisabled);
        textArea.setMouseTransparent(isDisabled);
    }

    public void setPlayerInfo(Player player) {
        Color playerColor = player.getColor();

        playerNameText.setText(player.getName() + "'s turn");
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
