package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Collection;
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
        if (currentController instanceof StartMenuController)
            ((StartMenuController) currentController).showMoney(money);
        else if (currentController instanceof ShopController)
            ((ShopController) currentController).showMoney(money);
    }

    public void gotoStartMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/StartMenuView.fxml"));
            Scene scene = new Scene(root);
            scene.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {

                Controller.stage.setX(event.getScreenX() - x);
                Controller.stage.setY(event.getScreenY() - y);

            });
            Controller.stage.setScene(scene);
        } catch (IOException e) {
        }
    }

    public void updateChatRoom(ServerChatRoomPacket packet) {
        if (currentController instanceof ChatRoomController)
            ((ChatRoomController) currentController).updateChatroom(packet.getMassages());
    }
}

