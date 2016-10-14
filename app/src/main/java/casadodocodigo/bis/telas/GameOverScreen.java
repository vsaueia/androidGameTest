package casadodocodigo.bis.telas;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import casadodocodigo.bis.R;
import casadodocodigo.bis.config.Assets;
import casadodocodigo.bis.controle.Button;
import casadodocodigo.bis.controle.ButtonDelegate;
import casadodocodigo.bis.controle.ScreenBackground;

import static casadodocodigo.bis.config.DeviceSettings.screenHeight;
import static casadodocodigo.bis.config.DeviceSettings.screenResolution;
import static casadodocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by viniciussilva on 14/10/2016.
 */

public class GameOverScreen extends CCLayer implements ButtonDelegate {
    private ScreenBackground background;
    private Button beginButton;

    public GameOverScreen() {
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0F, screenHeight() / 2.0F)));
        this.addChild(this.background);

        SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), R.raw.finalend, true);

        CCSprite title = CCSprite.sprite(Assets.GAMEOVER);
        title.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 130)));
        this.addChild(title);

        this.setIsTouchEnabled(true);
        this.beginButton = new Button(Assets.PLAY);
        this.beginButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 300)));
        this.beginButton.setDelegate(this);
        addChild(this.beginButton);
    }
    public CCScene scene() {
        CCScene scene = CCScene.node();
        scene.addChild(this);
        return scene;
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.beginButton)) {
            SoundEngine.sharedEngine().pauseSound();

            CCDirector.sharedDirector().replaceScene(new TitleScreen().scene());
        }
    }
}
