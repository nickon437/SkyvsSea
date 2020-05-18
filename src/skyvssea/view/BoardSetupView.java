package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import skyvssea.controller.BoardSetupController;
import skyvssea.util.ButtonUtil;
import skyvssea.util.ColorUtil;

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

	private TextField boardColTextField = new TextField();
	private TextField boardRowTextField = new TextField();
	private TextField bigPieceTextField = new TextField();
	private TextField midPieceTextField = new TextField();
	private TextField smallPieceTextField = new TextField();
	private TextField babyPieceTextField = new TextField();

	private Label tips = new Label("The input cannot be invailed ");
//	private Label limit1Label = new Label(" >= 4 ");
//	private Label limit2Label = new Label(" >= 4 ");
//	private Label limit3Label = new Label(" >= 1 ");
//	private Label limit4Label = new Label(" >= 1 ");
//	private Label limit5Label = new Label(" >= 1 ");
//	private Label limit6Label = new Label(" >= 1 ");

	
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
		
		inputPane.add(boardColTextField, 1, 0);
		inputPane.add(boardRowTextField, 1, 1);
		inputPane.add(bigPieceTextField, 1, 3);
		inputPane.add(midPieceTextField, 1, 4);
		inputPane.add(smallPieceTextField, 1, 5);
		inputPane.add(babyPieceTextField, 1, 6);
		
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

	private void formatButtonHolder(HBox holder, BoardSetupController controller) {
		Button confirmButton = new Button("Confirm");
		Button clearButton = new Button("Clear");
		holder.getChildren().addAll(confirmButton, clearButton);

		holder.setAlignment(Pos.CENTER);
		holder.setSpacing(20);
		formatConfirmBtn(confirmButton, controller,stage);
		formatClearBtn(clearButton, controller);
	}

	private void formatConfirmBtn(Button button, BoardSetupController controller, Stage stage) {
		ButtonUtil.formatStandardButton(button, ColorUtil.STANDARD_BUTTON_COLOR);
		button.setOnAction(e -> controller.handleConfirmBtn(this));
		stage.close();
	}
	
	private void formatClearBtn(Button button, BoardSetupController controller) {
		ButtonUtil.formatStandardButton(button, ColorUtil.SECONDARY_BUTTON_COLOR);
		button.setOnAction(e -> controller.handleClearBtn(this));
	}

	public TextField getBoardColTextField() {
		return boardColTextField;
	}
	public TextField getBoardRowTextField() {
		return boardRowTextField;
	}
	public TextField getBigPieceTextField() {
		return bigPieceTextField;
	}
	public TextField getMidPieceTextField() {
		return midPieceTextField;
	}
	public TextField getSmallPieceTextField() {
		return smallPieceTextField;
	}
	public TextField getBabyPieceTextField() {
		return babyPieceTextField;
	}
	public Label getTips() {
		return tips;
	}
}
