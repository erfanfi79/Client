package view.battleView;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import view.Constants;
import view.ImageLibrary;

public class HandView {

    private ImageView[] backgroundOfHandCards = new ImageView[6];
    private ImageView[] cardsImage = new ImageView[6];

    public AnchorPane get() {

        AnchorPane anchorPane = new AnchorPane();

        for (int i = 0; i < 6; i++) {
            backgroundOfHandCards[i] = new ImageView(ImageLibrary.HandCardAround.getImage());
            backgroundOfHandCards[i].setScaleX(0.7);
            backgroundOfHandCards[i].setScaleY(0.7);
            backgroundOfHandCards[i].getStyleClass().add("enterMouseOnHandCard");
            backgroundOfHandCards[i].relocate(getX(i), Constants.HAND_Pane_LAYOUT_Y.get());
            anchorPane.getChildren().add(backgroundOfHandCards[i]);
        }

        return null;
    }

//    public void setHandCards()

    private int getX(int index) {
        return Constants.HAND_Pane_LAYOUT_X.get() + Constants.HAND_H_DISTANCE_BETWEEN.get() * index;
    }
}
