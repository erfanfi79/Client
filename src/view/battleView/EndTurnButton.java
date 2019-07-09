package view.battleView;

import controller.ClientListener;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import packet.clientPacket.clientMatchPacket.ClientMatchEnum;
import packet.clientPacket.clientMatchPacket.ClientMatchEnumPacket;
import view.ImageLibrary;

public class EndTurnButton {

    private ImageView endTurnButtonImage;
    private StackPane endTurnButton;

    public StackPane get() {

        endTurnButtonImage = new ImageView(ImageLibrary.EndTurnButtonInOpponentTurn.getImage());
        Label endTurnLabel = new Label("END TURN");
        endTurnLabel.setTextFill(Color.WHITE);
        endTurnLabel.setFont(Font.font(20));

        endTurnButton = new StackPane();
        endTurnButton.getChildren().addAll(endTurnButtonImage, endTurnLabel);
        endTurnButton.relocate(1000, 570);

        endTurnButtonEventsHandler();

        return endTurnButton;
    }

    public void changeColor() {     //todo pay attention to change it in requests

        if (BattleView.isMyTurn()) {
            endTurnButtonImage.setImage(ImageLibrary.EndTurnButtonInMyTurn.getImage());
            endTurnButtonImage.getStyleClass().add("enterMouseOnEndTurnButton");

        } else {
            endTurnButtonImage.setImage(ImageLibrary.EndTurnButtonInOpponentTurn.getImage());
            endTurnButtonImage.getStyleClass().remove("enterMouseOnEndTurnButton");
        }
    }

    private void endTurnButtonEventsHandler() {

        endTurnButton.setOnMouseClicked(event -> {
            if (BattleView.isMyTurn()) {

                ClientListener.sendPacketToServer(new ClientMatchEnumPacket(ClientMatchEnum.END_TURN));
                changeColor();
            }
        });
    }
}
