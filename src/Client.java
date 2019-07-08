import controller.ClientListener;
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.Socket;


public class Client extends Application {

    private double x, y;

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8888);
            ClientListener clientListener = new ClientListener(socket);
            Controller.getInstance().clientListener = clientListener;
            clientListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.getInstance().stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("view/AccountMenuView.fxml"));

        Parent root = fxmlLoader.load();
        Controller.getInstance().currentController = fxmlLoader.getController();
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
