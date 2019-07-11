package view.battleView;

import controller.Controller;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import packet.clientPacket.clientMatchPacket.ClientMatchEnum;
import packet.clientPacket.clientMatchPacket.ClientMatchEnumPacket;
import view.ImageLibrary;

public class EndTurnButton {

    private BattleView battleView;
    private ImageView endTurnButtonImage;
    private StackPane endTurnButton;

    public StackPane get(BattleView battleView) {

        this.battleView = battleView;

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

    public void changeColor() {

        if (battleView.isMyTurn) {
            endTurnButtonImage.setImage(ImageLibrary.EndTurnButtonInMyTurn.getImage());
            endTurnButton.getStyleClass().add("enterMouseOnEndTurnButton");

        } else {
            endTurnButtonImage.setImage(ImageLibrary.EndTurnButtonInOpponentTurn.getImage());
            endTurnButton.getStyleClass().remove("enterMouseOnEndTurnButton");
        }
    }

    private void endTurnButtonEventsHandler() {

        endTurnButton.setOnMouseClicked(event -> {
            if (battleView.isMyTurn) {
                battleView.isMyTurn = false;
                Controller.getInstance().clientListener.sendPacketToServer(new ClientMatchEnumPacket(ClientMatchEnum.END_TURN));
                changeColor();
            }
        });
    }
}
