package it.polimi.ingsw.view.scenes;

import it.polimi.ingsw.model.lorenzo.token.ActionToken;
import it.polimi.ingsw.utils.ConstantValues;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class LorenzoDialog extends BasicSceneUpdater{

    @FXML
    ImageView tokenImage;

    String imgPath;
    public LorenzoDialog(String token)
    {
        System.out.println(token);
        imgPath = token;
    }

    @Override
    public void init() {
        super.init();
        tokenImage.setImage(loadImage(ConstantValues.tokens + this.imgPath));
    }


}
