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


    private boolean moveStatus ;
    private boolean killStatus ;
    private boolean specialStatus;
    private int skipStatus;

    private Player player1;
    private Player player2;

    //player text
    Text player1Text = new Text();

    Text player2Text = new Text();


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
        player1Text.setText(player1.getName());
        player1Text.setFill(Color.RED);
        player1Text.setFont(Font.font(15));
        player1Text.setVisible(true);


        player2Text.setText(player2.getName());
        player2Text.setFill(Color.BLUE);
        player2Text.setFont(Font.font(15));
        player2Text.setVisible(false);

        this.getChildren().addAll(player1Text,moveBtn, killBtn, specialEffectBtn,skipBtn,player2Text);
        this.setSpacing(20d);


        maximizeControlSize(moveBtn);
        maximizeControlSize(killBtn);
        maximizeControlSize(specialEffectBtn);
        maximizeControlSize(skipBtn);

        // if kill button and effect button have been activated, change player turn


        killBtn.setOnAction(actionEvent -> {
            killStatus = true;
            changeButtonStatus(player1,player2,killStatus,specialStatus);

        });

        specialEffectBtn.setOnAction(actionEvent -> {
            specialStatus = true;
            changeButtonStatus(player1,player2,killStatus,specialStatus);
        });

        //IF Click skip, change player turn
        skipBtn.setOnAction(actionEvent -> {

            if((player1.isStatus() == true) &&( player2.isStatus() == false) ){

                changePlayerStatus(player1,player2);
            }

            else if( (player1.isStatus() == false)&& (player2.isStatus() == true)){

                changePlayerStatus(player1,player2);
            }
        });


    }

    private void maximizeControlSize(Control control) {
        control.setPrefHeight(50d);
        HBox.setHgrow(control, Priority.ALWAYS);
        control.setMaxWidth(Double.MAX_VALUE);
    }


    private void changePlayerStatus(Player player, Player anotherplayer){
        if((player.isStatus() == true) && (anotherplayer.isStatus() == false) ){
            //visilize the player name
            player1Text.setVisible(true);
            player2Text.setVisible(false);

            //change players status
            player1.setStatus(false);
            player2.setStatus(true);
        }

        else if((player1.isStatus() == false) && (player2.isStatus() == true)){
            //visilize the player name
            player2Text.setVisible(true);
            player1Text.setVisible(false);

            //change players status
            player1.setStatus(true);
            player2.setStatus(false);
        }
    }

    private void changeButtonStatus(Player one,Player two, boolean b1, boolean b2){
        if((b1 == true) && (b2 == true)){
            changePlayerStatus(one,two);
            specialStatus = false;
            killStatus = false;

        }
    }

}
