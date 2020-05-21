package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import skyvssea.controller.BoardSetupController;
import skyvssea.model.Hierarchy;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;
import skyvssea.util.NodeCoordinateUtil;

import java.util.Map;
import java.util.TreeMap;

public class BoardSetupView extends VBox {

	private static final int MIN_BOARD_SIZE = 4;
	private static final int MAX_BOARD_SIZE = 20;
	private static final int DEFAULT_BOARD_SIZE = 10;
	private static final int MIN_NUM_PIECE = 1;
	private static final int MAX_NUM_PIECE = 5;
	private static final int DEFAULT_NUM_PIECE = 1;

	private Tooltip pieceTip = new Tooltip("The total number of pieces cannot be \n less than number of board rows");
	private Spinner<Integer> colSpinner = new Spinner<>(MIN_BOARD_SIZE, MAX_BOARD_SIZE * 2, DEFAULT_BOARD_SIZE);
	private Spinner<Integer> rowSpinner = new Spinner<>(MIN_BOARD_SIZE, MAX_BOARD_SIZE, DEFAULT_BOARD_SIZE);
	private Spinner<Integer> bigPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> mediumPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> smallPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> babyPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);

	private Button confirmButton = new Button("Confirm");

	public BoardSetupView(BoardSetupController controller) {
		GridPane inputPane = new GridPane();
		formatInputPane(inputPane);

		HBox buttonHolder = new HBox();
		formatButtonHolder(buttonHolder, controller);

		this.getChildren().addAll(inputPane, buttonHolder);
		this.setPadding(new Insets(30));
		this.setSpacing(20);
	}

	private void formatInputPane(GridPane inputPane) {
		inputPane.add(new Label("Columns:"), 0, 0);
		inputPane.add(new Label("Rows:"), 0, 1);
		inputPane.add(new Label("Big piece:"), 0, 3);
		inputPane.add(new Label("Middle piece:"), 0, 4);
		inputPane.add(new Label("Small piece:"), 0,5);
		inputPane.add(new Label("Baby piece:"), 0, 6);
		inputPane.add(colSpinner, 1, 0);
		inputPane.add(rowSpinner, 1, 1);
		inputPane.add(bigPieceSpinner, 1, 3);
		inputPane.add(mediumPieceSpinner, 1, 4);
		inputPane.add(smallPieceSpinner, 1, 5);
		inputPane.add(babyPieceSpinner, 1, 6);

		Separator separator = new Separator();
		inputPane.add(separator, 0, 2);
		GridPane.setColumnSpan(separator, 2);

		inputPane.setHgap(10);
		inputPane.setVgap(10);
		formatSpinnerTip();
		formatSpinners();
	}

	private void formatSpinnerTip() {
		rowSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		bigPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		mediumPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		smallPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		babyPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
	}

	private void formatSpinners() {
		colSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		rowSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		bigPieceSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		mediumPieceSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		smallPieceSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		babyPieceSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
	}

	private void formatButtonHolder(HBox holder, BoardSetupController controller) {
		holder.getChildren().addAll(confirmButton);
		holder.setAlignment(Pos.CENTER);
		holder.setSpacing(20);
		formatConfirmBtn(confirmButton, controller);
	}

	private void formatConfirmBtn(Button button, BoardSetupController controller) {
		ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
		button.setPrefSize(150, 50);
		button.setOnAction(e -> controller.handleConfirmBtn(this));
		button.setOnMouseEntered(e -> ButtonUtil.formatHoveringEffect(button, true));
		button.setOnMouseExited(e -> ButtonUtil.formatHoveringEffect(button, false));
	}

	public int[] getBoardSize() {
		int[] boardSize = new int[2]; // Nick - Is there any better value type for this?
		boardSize[0] = colSpinner.getValue();
		boardSize[1] = rowSpinner.getValue();
		return boardSize;
	}

	public Map<Hierarchy, Integer> getPieceLineup() {
		Map<Hierarchy, Integer> lineup = new TreeMap<>();
		lineup.put(Hierarchy.BIG, bigPieceSpinner.getValue());
		lineup.put(Hierarchy.MEDIUM, mediumPieceSpinner.getValue());
		lineup.put(Hierarchy.SMALL, smallPieceSpinner.getValue());
		lineup.put(Hierarchy.BABY, babyPieceSpinner.getValue());
		return lineup;
	}

	private int getTotalPieces() {
		int total = 0;
		total += bigPieceSpinner.getValue();
		total += mediumPieceSpinner.getValue();
		total += smallPieceSpinner.getValue();
		total += babyPieceSpinner.getValue();
		return total;
	}

	private void validateNumPiece() {
		final int OFFSET = 7;

		if (rowSpinner.getValue() < getTotalPieces()) {
			pieceTip.show(rowSpinner, NodeCoordinateUtil.getRightX(rowSpinner), NodeCoordinateUtil.getY(rowSpinner) - OFFSET);
			confirmButton.setDisable(true);
		} else {
			pieceTip.hide();
			confirmButton.setDisable(false);
		}
	}
}
