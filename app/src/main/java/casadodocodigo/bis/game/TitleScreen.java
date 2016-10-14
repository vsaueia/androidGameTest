package casadodocodigo.bis.game;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import casadodocodigo.bis.config.Assets;
import casadodocodigo.bis.controle.MenuButtons;
import casadodocodigo.bis.controle.ScreenBackground;

import static casadodocodigo.bis.config.DeviceSettings.screenHeight;
import static casadodocodigo.bis.config.DeviceSettings.screenResolution;
import static casadodocodigo.bis.config.DeviceSettings.screenWidth;

public class TitleScreen extends CCLayer {

    private ScreenBackground background;

    public TitleScreen() {
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(
                screenResolution(CGPoint.ccp(screenWidth() / 2.0F, screenHeight() / 2.0F)));
        this.addChild(this.background);

        CCSprite title = CCSprite.sprite(Assets.LOGO);
        title.setPosition(screenResolution(
                CGPoint.ccp(screenWidth() / 2, screenHeight() - 150)));
        this.addChild(title);

        MenuButtons menuLayer = new MenuButtons();
        this.addChild(menuLayer);
    }

    public CCScene scene() {
        CCScene scene = CCScene.node();
        scene.addChild(this);
        return scene;
    }
}
