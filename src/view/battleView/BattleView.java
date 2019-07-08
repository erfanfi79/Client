package view.battleView;

import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static view.Constants.*;

public class BattleView extends Application {

    private ConstantViews constantViews = new ConstantViews();

    private ImageView[] player1Mana = new ImageView[9];
    private ImageView[] player2Mana = new ImageView[9];
    private Label labelPlayer1HeroHP = new Label();
    private Label labelPlayer2HeroHP = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {
        showBattle(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showBattle(Stage mainStage) {

        Pane pane = new Pane();

        pane.getChildren().addAll(constantViews.getConstantViews());

        Scene scene = new Scene(pane, STAGE_WIDTH.get(), STAGE_HEIGHT.get());
        scene.getStylesheets().add(getClass().getResource("/ui/style/style.css").toExternalForm());
        scene.setCursor(new ImageCursor(ui.ImageLibrary.CursorImage.getImage()));
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}
