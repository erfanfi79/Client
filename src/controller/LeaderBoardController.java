package controller;

import controller.MediaController.GameSfxPlayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import packet.clientPacket.ClientEnum;
import packet.clientPacket.ClientEnumPacket;

import java.io.IOException;
import java.util.ArrayList;

public class LeaderBoardController {
    double x, y;
    @FXML
    private Button btnBack;

    @FXML
    private HBox item;
    @FXML
    private CheckBox checkBox;
    @FXML
    private VBox vBoxLeaderBoard;

    @FXML
    private Label labelRank;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelHighScore;

    @FXML
    void onlineCheckBox() {
        if (checkBox.isSelected())
            Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.LEADER_BOARD_ONLINE));
        else
            Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.LEADER_BOARD));
    }
    @FXML
    void gotoStartMenu() {
        new GameSfxPlayer().onSelect();
        Controller.getInstance().gotoStartMenu();
    }

    public void initializeLeaderboard(ArrayList<String> usernames, ArrayList<Integer> winsNum) {
        Node[] nodes = new Node[usernames.size()];
        for (int i = vBoxLeaderBoard.getChildren().size() - 1; i >= 0; i--) {
            vBoxLeaderBoard.getChildren().remove(vBoxLeaderBoard.getChildren().get(i));
        }

        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../view/ItemLeaderboard.fxml"));
                nodes[i] = fxmlLoader.load();
                LeaderBoardController personLeaderBoard = fxmlLoader.getController();
                personLeaderBoard.setInformation(i + 1, usernames.get(i), winsNum.get(i));
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #3c63a3");
                });
                vBoxLeaderBoard.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setInformation(int rank, String name, int wins) {
        labelUsername.setText(name);
        labelRank.setText(String.valueOf(rank));
        labelHighScore.setText(String.valueOf(wins));
    }
}
