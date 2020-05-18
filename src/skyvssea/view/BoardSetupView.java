package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import skyvssea.controller.BoardSetupController;
import skyvssea.model.Hierarchy;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;
import skyvssea.util.NodeCoordinateUtil;

import java.util.HashMap;
import java.util.Map;

public class BoardSetupView extends VBox {


	private static final int MIN_BOARD_SIZE = 4;
	private static final int MAX_BOARD_SIZE = 20;
	private static final int DEFAULT_BOARD_SIZE = 10;
	private static final int MIN_NUM_PIECE = 1;
	private static final int MAX_NUM_PIECE = 5;
	private static final int DEFAULT_NUM_PIECE = 1;

	private Stage stage = new Stage();
	public GridPane inputPane = new GridPane();
	
//	private Text tip = new Text("The total number of pieces \n should be less than board rows");

	private Tooltip pieceTip = new Tooltip("The total number of pieces cannot be \n less than number of board rows");
	private Spinner<Integer> colSpinner = new Spinner<>(MIN_BOARD_SIZE, MAX_BOARD_SIZE, DEFAULT_BOARD_SIZE);
	private Spinner<Integer> rowSpinner = new Spinner<>(MIN_BOARD_SIZE, MAX_BOARD_SIZE, DEFAULT_BOARD_SIZE);
	private Spinner<Integer> bigPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> mediumPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> smallPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> babyPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);

	private boolean isInputValid = true;

	public BoardSetupView(BoardSetupController controller) {
		this.setSpacing(20);

		Label rowLabel = new Label("Rows: ");
		Label colLabel = new Label("Columns:");
		Label bigPieceLabel = new Label("Big piece:");
		Label midPieceLabel = new Label("Middle piece:");
		Label smallPieceLabel = new Label("Small piece:");
		Label babyPieceLabel = new Label("Baby piece:");

		inputPane.add(colLabel, 0, 0);
		inputPane.add(rowLabel, 0, 1);
		inputPane.add(bigPieceLabel, 0, 3);
		inputPane.add(midPieceLabel, 0, 4);
		inputPane.add(smallPieceLabel, 0,5);
		inputPane.add(babyPieceLabel, 0, 6);
		
		inputPane.add(colSpinner, 1, 0);
		inputPane.add(rowSpinner, 1, 1);
		inputPane.add(bigPieceSpinner, 1, 3);
		inputPane.add(mediumPieceSpinner, 1, 4);
		inputPane.add(smallPieceSpinner, 1, 5);
		inputPane.add(babyPieceSpinner, 1, 6);
		
		inputPane.setHgap(10);
		inputPane.setVgap(10);
		inputPane.setAlignment(Pos.CENTER);

		formatSpinnerTip();

		HBox buttonHolder = new HBox();
		formatButtonHolder(buttonHolder, controller);

		this.getChildren().addAll(inputPane, buttonHolder);
		this.setPadding(new Insets(30));
	}

	private void formatSpinnerTip() {
		rowSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		bigPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		mediumPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		smallPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
		babyPieceSpinner.valueProperty().addListener((observable, oldValue, newValue) -> validateNumPiece());
	}

	private void formatButtonHolder(HBox holder, BoardSetupController controller) {
		Button confirmButton = new Button("Confirm");
		holder.getChildren().addAll(confirmButton);

		holder.setAlignment(Pos.CENTER);
		holder.setSpacing(20);
		formatConfirmBtn(confirmButton, controller,stage);
	}

	private void formatConfirmBtn(Button button, BoardSetupController controller, Stage stage) {
		ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
		button.setPrefSize(150, 50);
		button.setOnAction(e -> controller.handleConfirmBtn(this));
		stage.close();
	}

	public int[] getBoardSize() {
		int[] boardSize = new int[2]; // Nick - Is there any better value type for this?
		boardSize[0] = rowSpinner.getValue().intValue();
		boardSize[1] = colSpinner.getValue().intValue();
		return boardSize;
	}

	public Map<Hierarchy, Integer> getPieceLineup() {
		Map<Hierarchy, Integer> lineup = new HashMap<>();
		lineup.put(Hierarchy.BIG, bigPieceSpinner.getValue());
		lineup.put(Hierarchy.MEDIUM, mediumPieceSpinner.getValue());
		lineup.put(Hierarchy.SMALL, smallPieceSpinner.getValue());
		lineup.put(Hierarchy.BABY, babyPieceSpinner.getValue());
		return lineup;
	}

	public int getTotalPieces() {
		int total = 0;
		total += bigPieceSpinner.getValue();
		total += mediumPieceSpinner.getValue();
		total += smallPieceSpinner.getValue();
		total += babyPieceSpinner.getValue();
		return total;
	}

	public void validateNumPiece() {
		final int OFFSET = 7;

		if (rowSpinner.getValue() <= getTotalPieces()) {
			pieceTip.show(rowSpinner,
					NodeCoordinateUtil.getRightX(rowSpinner),
					NodeCoordinateUtil.getY(rowSpinner) - OFFSET);
			isInputValid = false;
		} else {
			pieceTip.hide();
			isInputValid = true;
		}
	}

	public boolean isInputValid() {
		return isInputValid;
	}
}
