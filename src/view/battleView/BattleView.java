package view.battleView;

import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import packet.serverPacket.serverMatchPacket.VirtualCard;

import static view.Constants.STAGE_HEIGHT;
import static view.Constants.STAGE_WIDTH;

public class BattleView {

    private ConstantViews constantViews = new ConstantViews();
    public HeaderView headerView = new HeaderView();
    private TableInputHandler tableInputHandler = new TableInputHandler();
    public TableUnitsView tableUnitsView = new TableUnitsView();
    public EndTurnButton endTurnButton = new EndTurnButton();

    public VirtualCard[][] table;

    public boolean isMyTurn = false;
    boolean isReadyForInsert = false;
    int whichHandCardForInsert = -1;


    public void showBattle(Stage mainStage) {

        Pane pane = new Pane();
        //GameMusicPlayer.getInstance().playBattleMusic();

        System.err.println("before pane");
        pane.getChildren().addAll(constantViews.get(), headerView.get(), tableInputHandler.get(this),
                endTurnButton.get(this), tableUnitsView.get(this));
        System.err.println("after pane");

        Scene scene = new Scene(pane, STAGE_WIDTH.get(), STAGE_HEIGHT.get());
        scene.getStylesheets().add(getClass().getResource("/resources/style/BattleStyle.css").toExternalForm());
        scene.setCursor(new ImageCursor(view.ImageLibrary.CursorImage.getImage()));
        mainStage.setScene(scene);
        mainStage.show();
    }
}
