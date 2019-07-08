package view.battleView;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import view.Constants;
import view.ImageLibrary;

public class ConstantViews {

    public AnchorPane getConstantViews() {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(getBackground(), getHeaderImages());
        anchorPane.setMouseTransparent(true);
        return anchorPane;
    }

    private ImageView getBackground() {

        ImageView background = new ImageView(ImageLibrary.Background.getImage());
        background.setFitWidth(Constants.STAGE_WIDTH.get());
        background.setFitHeight(Constants.STAGE_HEIGHT.get());
        return background;
    }

    private AnchorPane getHeaderImages() {

        AnchorPane headerImage = new AnchorPane();

        ImageView leftImageHeader = new ImageView(ImageLibrary.LeftImageHeader.getImage());
        ImageView rightImageHeader = new ImageView(ImageLibrary.RightImageHeader.getImage());

        leftImageHeader.setScaleX(.4);
        leftImageHeader.setScaleY(.4);
        rightImageHeader.setScaleX(.4);
        rightImageHeader.setScaleY(.4);

        leftImageHeader.setLayoutX(-150);
        leftImageHeader.setLayoutY(-78);
        rightImageHeader.setLayoutX(798);
        rightImageHeader.setLayoutY(-53);

        headerImage.getChildren().addAll(leftImageHeader, rightImageHeader);
        return headerImage;
    }
}
