package casadodocodigo.bis.controle;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import casadodocodigo.bis.config.Assets;
import casadodocodigo.bis.game.GameScene;

import static casadodocodigo.bis.config.DeviceSettings.screenResolution;
import static casadodocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by viniciussilva on 06/10/2016.
 */

public class GameButtons extends CCLayer implements ButtonDelegate {
    private Button leftControl;
    private Button rightControl;
    private Button shootButton;
    private GameScene delegate;

    public static GameButtons gameButtons() {
        return new GameButtons();
    }

    public GameButtons() {
        this.setIsTouchEnabled(true);

        this.leftControl = new Button(Assets.LEFTCONTROL);
        this.rightControl = new Button(Assets.RIGHTCONTROL);
        this.shootButton = new Button(Assets.SHOOTBUTTON);

        this.leftControl.setDelegate(this);
        this.rightControl.setDelegate(this);
        this.shootButton.setDelegate(this);

        setButtonsPosition();

        addChild(leftControl);
        addChild(rightControl);
        addChild(shootButton);
    }

    private void setButtonsPosition() {
        leftControl.setPosition(screenResolution(
                CGPoint.ccp(40, 40)));
        rightControl.setPosition(screenResolution(
                CGPoint.ccp(100, 40)));
        shootButton.setPosition(screenResolution(
                CGPoint.ccp(screenWidth()-40, 40)));
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.leftControl)) {
            this.delegate.moveLeft();
        }
        if (sender.equals(this.rightControl)) {
            this.delegate.moveRight();
        }
        if (sender.equals(this.shootButton)) {
            this.delegate.shoot();
        }
    }

    public void setDelegate(GameScene delegate) {
        this.delegate = delegate;
    }

}
