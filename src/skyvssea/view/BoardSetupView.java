package skyvssea.view;

import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
	private Stage stage = new Stage();
	public GridPane inputPane = new GridPane();
	
//	private Text tip = new Text("The total number of pieces \n should be less than board rows");
	
	private Label rowLabel = new Label("Rows: ");
	private Label colLabel = new Label("Columns:");
	private Label bigPieceLabel = new Label("Big piece:");
	private Label midPieceLabel = new Label("Middle piece:");
	private Label smallPieceLabel = new Label("Small piece:");
	private Label babyPieceLabel = new Label("Baby piece:");

//	private TextField boardColTextField = new TextField();
//	private TextField boardRowTextField = new TextField();
//	private TextField bigPieceTextField = new TextField();
//	private TextField midPieceTextField = new TextField();
//	private TextField smallPieceTextField = new TextField();
//	private TextField babyPieceTextField = new TextField();

	private Spinner<Integer> colSpinner = new Spinner<>(boardSizeValues);
	private Spinner<Integer> rowSpinner = new Spinner<>(boardSizeValues);
	private Spinner<Integer> bigPieceSpinner = new Spinner<>(numPieceValues);
	private Spinner<Integer> mediumPieceSpinner = new Spinner<>(numPieceValues);
	private Spinner<Integer> smallPieceSpinner = new Spinner<>(numPieceValues);
	private Spinner<Integer> babyPieceSpinner = new Spinner<>(numPieceValues);

	private Label tips = new Label("The input cannot be invailed ");
//	private Label limit1Label = new Label(" >= 4 ");
//	private Label limit2Label = new Label(" >= 4 ");
//	private Label limit3Label = new Label(" >= 1 ");
//	private Label limit4Label = new Label(" >= 1 ");
//	private Label limit5Label = new Label(" >= 1 ");
//	private Label limit6Label = new Label(" >= 1 ");

	private static final SpinnerValueFactory<Integer> boardSizeValues =
			new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 20, 10);
	private static final SpinnerValueFactory<Integer> numPieceValues =
			new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);

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
		
//		inputPane.add(limit1Label, 2, 0);
//		inputPane.add(limit2Label, 2, 1);
//		inputPane.add(limit3Label, 2, 3);
//		inputPane.add(limit4Label, 2, 4);
//		inputPane.add(limit5Label, 2, 5);
//		inputPane.add(limit6Label, 2, 6);
		
		inputPane.setHgap(10);
		inputPane.setVgap(10);
		inputPane.setAlignment(Pos.CENTER);

		HBox buttonHolder = new HBox();
		formatButtonHolder(buttonHolder, controller);

		this.getChildren().addAll(inputPane, buttonHolder);
		this.setPadding(new Insets(30));
	}

	private void formatSpinner(Spinner<Integer> spinner) {
		spinner.setValueFactory(boardSizeValues);
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
