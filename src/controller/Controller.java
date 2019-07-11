package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Collection;
import packet.clientPacket.ClientCheatPacket;
import packet.serverPacket.ServerChatRoomPacket;

import java.io.IOException;

public class Controller {
    private static Controller controller;
    private Collection myCollection;
    public ShopController shopController = null;
    public CollectionController collectionController = null;
    public ClientListener clientListener;
    public Object currentController;
    public static Stage stage;
    public double x, y;

    public Collection getMyCollection() {
        return myCollection;
    }

    public void setMyCollection(Collection myCollection) {
        this.myCollection = myCollection;
    }

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    private Controller() {
    }

    public void showMoney(String money) {
        System.out.println(32);
        if (currentController instanceof StartMenuController)
            ((StartMenuController) currentController).showMoney(money);
        else if (currentController instanceof ShopController)
            ((ShopController) currentController).showMoney(money);
    }

    public void gotoStartMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/StartMenuView.fxml"));
            Scene scene = new Scene(root);

            scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                ClientCheatPacket packet = new ClientCheatPacket();
                switch (key.getCode()) {
                    case F8:
                        packet.setDoubleMoney();
                        clientListener.sendPacketToServer(packet);
                        break;
                    case F7:
                        packet.setHalfPrice();
                        clientListener.sendPacketToServer(packet);
                        break;
                    case F9:
                        packet.setEqualSell();
                        clientListener.sendPacketToServer(packet);
                        break;
                }
            });
            scene.setOnMouseDragged(event -> {

                Controller.stage.setX(event.getScreenX() - x);
                Controller.stage.setY(event.getScreenY() - y);

            });
            scene.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            Controller.stage.setScene(scene);
        } catch (IOException e) {
        }
    }

    public void updateChatRoom(ServerChatRoomPacket packet) {
        if (currentController instanceof ChatRoomController)
            ((ChatRoomController) currentController).updateChatroom(packet.getMassages());
    }

    public void handleSuccsesfulLogs() {
        if (currentController instanceof StartMenuController) {
            ((StartMenuController) currentController).validDeckToEnterBattleMenu();
            return;
        }
        new Popup().showMessage("Task Compeleted");
    }
}

