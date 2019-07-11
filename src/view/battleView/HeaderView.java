package view.battleView;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.ImageLibrary;

public class HeaderView {

//    private Label labelPlayer1HeroHP = new Label();
//    private Label labelPlayer2HeroHP = new Label();
    private Label player1Name = new Label();
    private Label player2Name = new Label();
    private ImageView[] player1Mana = new ImageView[9];
    private ImageView[] player2Mana = new ImageView[9];


    public AnchorPane get() {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(getManas(), getPlayersName());
        anchorPane.setMouseTransparent(true);
        return anchorPane;
    }


    private AnchorPane getPlayersName() {

        AnchorPane headerLeftGameInfo = new AnchorPane();
        AnchorPane headerRightGameInfo = new AnchorPane();

        player1Name.setScaleX(2);
        player1Name.setScaleY(2);
        player2Name.setScaleX(2);
        player2Name.setScaleY(2);

        player1Name.setFont(Font.font("Dyuhti", 20));
        player2Name.setFont(Font.font("Dyuhti", 20));

        player1Name.setTextFill(Color.WHITE);
        player2Name.setTextFill(Color.WHITE);

        headerLeftGameInfo.getChildren().add(player2Name);
        headerRightGameInfo.getChildren().add(player1Name);

        headerLeftGameInfo.relocate(450, 50);
        headerRightGameInfo.relocate(830, 50);

        player1Name.setAlignment(Pos.CENTER);
        player2Name.setAlignment(Pos.CENTER);

        headerLeftGameInfo.setRotate(4);
        headerRightGameInfo.setRotate(-4);

        AnchorPane playersName = new AnchorPane();
        playersName.getChildren().addAll(headerLeftGameInfo, headerRightGameInfo);
        playersName.relocate(0, 0);
        return playersName;
    }

    public void setPlayersName(String player1Name, String player2Name) {

        this.player1Name.setText(player1Name);
        this.player2Name.setText(player2Name);
    }


//    private AnchorPane getHerosHP() {
//
//        AnchorPane anchorPane = new AnchorPane();
//
//        labelPlayer1HeroHP.relocate(180, 75);
//        labelPlayer2HeroHP.relocate(1090, 75);
//
//        labelPlayer1HeroHP.setScaleX(1.5);
//        labelPlayer1HeroHP.setScaleY(1.5);
//        labelPlayer2HeroHP.setScaleX(1.5);
//        labelPlayer2HeroHP.setScaleY(1.5);
//
//        labelPlayer1HeroHP.setTextFill(Color.WHITE);
//        labelPlayer2HeroHP.setTextFill(Color.WHITE);
//
//        anchorPane.getChildren().addAll(labelPlayer1HeroHP, labelPlayer2HeroHP);
//        return anchorPane;
//    }
//
//    public void setHerosHP(int player1HeroHP, int Player2HeroHP) {
//
//        labelPlayer1HeroHP.setText(player1HeroHP + "");
//        labelPlayer2HeroHP.setText(Player2HeroHP + "");
//    }


    private AnchorPane getManas() {

        HBox player1ManaBox = new HBox();
        HBox player2ManaBox = new HBox();

        for (int i = 0; i < 9 /*numberOfAllMana*/; i++) {
            player1Mana[i] = new ImageView();
            player2Mana[i] = new ImageView();
        }

        for (int i = 8; i >= 0; i--) player1ManaBox.getChildren().add(player1Mana[i]);
        for (int i = 0; i < 9 /*numberOfAllMana*/; i++) player2ManaBox.getChildren().add(player2Mana[i]);

        player2ManaBox.relocate(320, 70);
        player1ManaBox.relocate(710, 70);
        player2ManaBox.setSpacing(-35);
        player1ManaBox.setSpacing(-35);

        player1ManaBox.setRotate(-3);
        player2ManaBox.setRotate(3);

        AnchorPane manasPane = new AnchorPane();
        manasPane.getChildren().addAll(player1ManaBox, player2ManaBox);
        return manasPane;
    }

    public void setManas(int numberOfPlayer1Mana, int numberOfPlayer2Mana) {

        for (int i = 0; i < numberOfPlayer1Mana; i++) {

            player1Mana[i].setImage(ImageLibrary.FillMana.getImage());
            player1Mana[i].setScaleX(0.4);
            player1Mana[i].setScaleY(0.4);
            player1Mana[i].getStyleClass().add("mana-bar-item");
        }

        for (int i = numberOfPlayer1Mana; i < 9 /*numberOfAllMana*/; i++) {

            player1Mana[i].setImage(ImageLibrary.EmptyMana.getImage());
            player1Mana[i].setScaleX(0.4);
            player1Mana[i].setScaleY(0.4);
            player1Mana[i].getStyleClass().add("mana-bar-item");
        }

        for (int i = numberOfPlayer2Mana; i < 9 /*numberOfAllMana*/; i++) {

            player2Mana[i].setImage(ImageLibrary.EmptyMana.getImage());
            player2Mana[i].setScaleX(0.4);
            player2Mana[i].setScaleY(0.4);
            player2Mana[i].getStyleClass().add("mana-bar-item");
        }

        for (int i = 0; i < numberOfPlayer2Mana; i++) {

            player2Mana[i].setImage(ImageLibrary.FillMana.getImage());
            player2Mana[i].setScaleX(0.4);
            player2Mana[i].setScaleY(0.4);
            player2Mana[i].getStyleClass().add("mana-bar-item");
        }
    }
}
