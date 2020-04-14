package skyvssea.view;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.text.Text;
import skyvssea.model.Player;

public class ActionPane extends HBox {
    Button moveBtn = new Button("Move");
    Button killBtn = new Button("Kill");
    Button specialEffectBtn = new Button("Special Effect");
    Button skipBtn = new Button("Skip");


    private int moveStutas;
    private int killStutas;
    private int effectStutas;
    private int skipStutas;

    private Player player1;
    private Player player2;

    public ActionPane() {
        // player setted and start from player1
        player1 = new Player("shark leader",Player.RED_COLOUR);
        player2 = new Player("eagle leader",Player.BLUE_COLOUR);

        player1.setStatus(true);

        //button style
        moveBtn.setFont(Font.font(20));
        killBtn.setFont(Font.font(20));
        specialEffectBtn.setFont(Font.font(20));
        skipBtn.setFont(Font.font(20));

        //player text
        Text player1Text = new Text();
        player1Text.setText(player1.getName());
        player1Text.setFill(Color.RED);
        player1Text.setFont(Font.font(15));
        player1Text.setVisible(true);

        Text player2Text = new Text();
        player2Text.setText(player2.getName());
        player2Text.setFill(Color.BLUE);
        player2Text.setFont(Font.font(15));
        player2Text.setVisible(true);

        this.getChildren().addAll(player1Text,moveBtn, killBtn, specialEffectBtn,skipBtn,player2Text);
        this.setSpacing(20d);


        //this.setStyle("-fx-background-color: #ffb578;"); // TODO: Remove this later

        maximizeControlSize(moveBtn);
        maximizeControlSize(killBtn);
        maximizeControlSize(specialEffectBtn);
        maximizeControlSize(skipBtn);

        //IF Click skip, change player turn
        skipBtn.setOnAction(actionEvent -> {

            if((player1.isStatus() == true) &&( player2.isStatus() == false) ){
                //visilize the player name
                player1Text.setVisible(true);
                player2Text.setVisible(false);
                // change button color
//                moveBtn.setStyle("-fx-background-color: #e74c3c");
//                killBtn.setStyle("-fx-background-color: #e74c3c");
//                specialEffectBtn.setStyle("-fx-background-color: #e74c3c");
//                skipBtn.setStyle("-fx-background-color: #e74c3c");

                //change players status
                player1.setStatus(false);
                player2.setStatus(true);
            }

            else if( (player1.isStatus() == false)&& (player2.isStatus() == true)){
                //visilize the player name
                player2Text.setVisible(true);
                player1Text.setVisible(false);
                // change button color
//                moveBtn.setStyle("-fx-background-color: #3498db");
//                killBtn.setStyle("-fx-background-color: #3498db");
//                specialEffectBtn.setStyle("-fx-background-color: #3498db");
//                skipBtn.setStyle("-fx-background-color: #3498db");

                //change players status
                player1.setStatus(true);
                player2.setStatus(false);
            }
        });
    }

    private void maximizeControlSize(Control control) {
        control.setPrefHeight(50d);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }
}
