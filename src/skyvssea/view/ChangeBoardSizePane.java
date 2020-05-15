package skyvssea.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import skyvssea.controller.Controller;

public class ChangeBoardSizePane {
	public Stage stage = new Stage();
	public GridPane gridPane;
	public FlowPane tipFlowPane = new FlowPane();
	
	public Text tip = new Text("The total number of pieces \n should be less than board rows");
//	public Text tip2 = new Text("n");
	
	public Label boardRowLabel = new Label("Board Row ");
	public Label boardColLabel = new Label("Board Column");
	
	public Label bigPieceNumLabel = new Label("Big Piece Number:");
	public Label midPieceNumLabel = new Label("Middle Piece Number:");
	public Label smallPieceNumLabel = new Label("Small Piece Number:");
	public Label babyPieceNumLabel = new Label("Baby Piece Number:");
	
	public TextField boardColTextField = new TextField();
	public TextField boardRowTextField = new TextField();
	public TextField bigPieceTextField = new TextField();
	public TextField midPieceTextField = new TextField();
	public TextField smallPieceTextField = new TextField();
	public TextField babyPieceTextField = new TextField();
	
	public Label tips = new Label("The input cannot be invailed ");
	public Label limitColLabel = new Label(" >= 4 ");
	public Label limitRowLabel = new Label(" >= 4 ");
	public Label limitBigLabel = new Label(" >= 1 ");
	public Label limitMidLabel = new Label(" >= 1 ");
	public Label limitSmallLabel = new Label(" >= 1 ");
	public Label limitBabyLabel = new Label(" >= 1 ");
	
	public Button confirmButton = new Button("confirm");
	public Button clearButton = new Button(" clear ");
	
	public ChangeBoardSizePane( Controller controller) {
		
		boardRowLabel.setFont(Font.font(15));
		boardColLabel.setFont(Font.font(15));
		bigPieceNumLabel.setFont(Font.font(15));
		midPieceNumLabel.setFont(Font.font(15));
		smallPieceNumLabel.setFont(Font.font(15));
		babyPieceNumLabel.setFont(Font.font(15));
		clearButton.setFont(Font.font(15));
		confirmButton.setFont(Font.font(15));
		
		tips.setTextFill(Color.RED);
		tips.setVisible(false);
		
		this.gridPane = new GridPane();
		this.tipFlowPane = new FlowPane();
		
		gridPane.add(boardColLabel, 0, 0);
		gridPane.add(boardRowLabel, 0, 1);
		gridPane.add(tip, 0, 2);
		gridPane.add(bigPieceNumLabel, 0, 3);
		gridPane.add(midPieceNumLabel, 0, 4);
		gridPane.add(smallPieceNumLabel, 0,5);
		gridPane.add(babyPieceNumLabel, 0, 6);
		gridPane.add(tips, 0, 7);
		gridPane.add(confirmButton, 0, 8);
		
		gridPane.add(boardColTextField, 1, 0);
		gridPane.add(boardRowTextField, 1, 1);
		gridPane.add(bigPieceTextField, 1, 3);
		gridPane.add(midPieceTextField, 1, 4);
		gridPane.add(smallPieceTextField, 1, 5);
		gridPane.add(babyPieceTextField, 1, 6);
		gridPane.add(clearButton, 1, 8);
		
		gridPane.add(limitColLabel, 2, 0);
		gridPane.add(limitRowLabel, 2, 1);
		gridPane.add(limitBigLabel, 2, 3);
		gridPane.add(limitMidLabel, 2, 4);
		gridPane.add(limitSmallLabel, 2, 5);
		gridPane.add(limitBabyLabel, 2, 6);
		
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		gridPane.setAlignment(Pos.CENTER);
		
		GridPane.setMargin(confirmButton, new Insets(0, 0, 0, 30));
		GridPane.setMargin(clearButton, new Insets(0, 0, 0, 60));
		
		formatClearBtn(controller);
		formatConfirmBtn(controller,stage);
	}
	
	private void formatClearBtn(Controller controller) {
		clearButton.setOnAction(e -> controller.handleClearBtn(this));
	}
	
	private void formatConfirmBtn(Controller controller,Stage stage) {
		confirmButton.setOnAction(e -> controller.handleConfirmBtn(this,stage));
		stage.close();
	}

	public GridPane getGridPane() {
		return gridPane;
	}

	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}


	public TextField getBoardColTextField() {
		return boardColTextField;
	}


	public void setBoardColTextField(TextField boardColTextField) {
		this.boardColTextField = boardColTextField;
	}


	public TextField getBoardRowTextField() {
		return boardRowTextField;
	}


	public void setBoardRowTextField(TextField boardRowTextField) {
		this.boardRowTextField = boardRowTextField;
	}


	public TextField getBigPieceTextField() {
		return bigPieceTextField;
	}


	public void setBigPieceTextField(TextField bigPieceTextField) {
		this.bigPieceTextField = bigPieceTextField;
	}


	public TextField getMidPieceTextField() {
		return midPieceTextField;
	}


	public void setMidPieceTextField(TextField midPieceTextField) {
		this.midPieceTextField = midPieceTextField;
	}


	public TextField getSmallPieceTextField() {
		return smallPieceTextField;
	}


	public void setSmallPieceTextField(TextField smallPieceTextField) {
		this.smallPieceTextField = smallPieceTextField;
	}


	public TextField getBabyPieceTextField() {
		return babyPieceTextField;
	}


	public void setBabyPieceTextField(TextField babyPieceTextField) {
		this.babyPieceTextField = babyPieceTextField;
	}


	public Label getTips() {
		return tips;
	}


	public void setTips(Label tips) {
		this.tips = tips;
	}


	public Button getConfirmButton() {
		return confirmButton;
	}


	public void setConfirmButton(Button confirmButton) {
		this.confirmButton = confirmButton;
	}


	public Button getClearButton() {
		return clearButton;
	}


	public void setClearButton(Button clearButton) {
		this.clearButton = clearButton;
	}

	
	
	
}
