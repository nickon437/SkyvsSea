package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import skyvssea.controller.BoardSetupController;
import skyvssea.model.Hierarchy;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;

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
	
	private Label rowLabel = new Label("Rows: ");
	private Label colLabel = new Label("Columns:");
	private Label bigPieceLabel = new Label("Big piece:");
	private Label midPieceLabel = new Label("Middle piece:");
	private Label smallPieceLabel = new Label("Small piece:");
	private Label babyPieceLabel = new Label("Baby piece:");

	private Spinner<Integer> colSpinner = new Spinner<>(MIN_BOARD_SIZE, MAX_BOARD_SIZE, DEFAULT_BOARD_SIZE);
	private Spinner<Integer> rowSpinner = new Spinner<>(MIN_BOARD_SIZE, MAX_BOARD_SIZE, DEFAULT_BOARD_SIZE);
	private Spinner<Integer> bigPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> mediumPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> smallPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);
	private Spinner<Integer> babyPieceSpinner = new Spinner<>(MIN_NUM_PIECE, MAX_NUM_PIECE, DEFAULT_NUM_PIECE);

	private Label tips = new Label("The input cannot be invailed ");

	public BoardSetupView(BoardSetupController controller) {
		tips.setTextFill(Color.RED);
		tips.setVisible(false);

		inputPane.add(colLabel, 0, 0);
		inputPane.add(rowLabel, 0, 1);
//		inputPane.add(tip, 0, 2);
		inputPane.add(bigPieceLabel, 0, 3);
		inputPane.add(midPieceLabel, 0, 4);
		inputPane.add(smallPieceLabel, 0,5);
		inputPane.add(babyPieceLabel, 0, 6);
		inputPane.add(tips, 0, 7);
		
		inputPane.add(colSpinner, 1, 0);
		inputPane.add(rowSpinner, 1, 1);
		inputPane.add(bigPieceSpinner, 1, 3);
		inputPane.add(mediumPieceSpinner, 1, 4);
		inputPane.add(smallPieceSpinner, 1, 5);
		inputPane.add(babyPieceSpinner, 1, 6);
		
		inputPane.setHgap(10);
		inputPane.setVgap(10);
		inputPane.setAlignment(Pos.CENTER);

		HBox buttonHolder = new HBox();
		formatButtonHolder(buttonHolder, controller);

		this.getChildren().addAll(inputPane, buttonHolder);
		this.setPadding(new Insets(30));
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
		button.setPrefSize(250, 50);
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

	public Label getTips() {
		return tips;
	}
}
