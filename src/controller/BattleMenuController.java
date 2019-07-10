package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.MatchType;
import packet.clientPacket.ClientEnum;
import packet.clientPacket.ClientEnumPacket;
import view.battleView.BattleView;

public class BattleMenuController {
    double x, y;
    private MatchType matchType;
    @FXML
    private ImageView cancel;

    @FXML
    private AnchorPane singlePlayerPane;

    @FXML
    private TextField flagsNum;

    @FXML
    private Label findingLabel;

    @FXML
    private Button btnMultiPlayer;

    @FXML
    private ToggleGroup mode;

    @FXML
    private RadioButton radioMode1;

    @FXML
    private RadioButton radioMode3;

    @FXML
    private RadioButton radioMode2;

    @FXML
    private Button btnMode3;

    @FXML
    private ImageView loadingGif;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnMode1;

    @FXML
    private Button btnMode2;


    @FXML
    void gotoStartMenu() {
        Controller.getInstance().gotoStartMenu();
    }


    @FXML
    void playSinglePlayer() {

        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.SINGLE_PLAYER));

        BattleView battleView = new BattleView();
        battleView.showBattle(Controller.stage);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(battleView::inputHandler).start();
    }

    @FXML
    public void startMultiPlayer() {
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.MULTI_PLAYER));

    }

    @FXML
    void storyMode(ActionEvent event) {
    }

    @FXML
    void mouseIn(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            button.setOpacity(1);

        }
    }

    @FXML
    void mouseOut(MouseEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            button.setOpacity(0.8);
        }
    }


    public void startMultiPlayerGame() {

    }


    @FXML
    void cancelFinding() {
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.CANCEL_WAITING_FOR_MULTI_PLAYER_GAME));
        mainPane.setDisable(false);
        cancel.setVisible(false);
        loadingGif.setVisible(false);
        findingLabel.setVisible(false);
    }



}
