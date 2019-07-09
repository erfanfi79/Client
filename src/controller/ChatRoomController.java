package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import packet.clientPacket.ClientChatRoomPacket;
import packet.clientPacket.ClientEnum;
import packet.clientPacket.ClientEnumPacket;
import packet.serverPacket.Massage;

import java.util.ArrayList;

public class ChatRoomController {
    double x, y;
    @FXML
    private TextField txtMessage;

    @FXML
    private VBox vBoxChat;

    @FXML
    void gotoStartMenu() {
        Controller.getInstance().gotoStartMenu();
        Controller.getInstance().clientListener.sendPacketToServer(new ClientEnumPacket(ClientEnum.EXIT_CHATROOM));
    }

    public void updateChatroom(ArrayList<Massage> massages) {
        for (int i = vBoxChat.getChildren().size() - 1; i >= 0; i--)
            vBoxChat.getChildren().remove(vBoxChat.getChildren().get(i));

        for (Massage massage : massages) {
            Label label = new Label();
            label.setText(massage.getUserName() + "  : " + massage.getMassage());
            vBoxChat.getChildren().add(label);
        }

    }

    @FXML
    void sendMessage() {
        if (txtMessage.getText().trim().isEmpty())
            return;
        ClientChatRoomPacket clientChatRoomPacket = new ClientChatRoomPacket();
        clientChatRoomPacket.setString(txtMessage.getText());
        Controller.getInstance().clientListener.sendPacketToServer(clientChatRoomPacket);
        txtMessage.setText("");
    }
}
