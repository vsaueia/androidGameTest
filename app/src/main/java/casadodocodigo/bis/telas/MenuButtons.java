package casadodocodigo.bis.telas;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import casadodocodigo.bis.config.Assets;
import casadodocodigo.bis.cenas.GameScene;
import casadodocodigo.bis.controle.Button;
import casadodocodigo.bis.controle.ButtonDelegate;

import static casadodocodigo.bis.config.DeviceSettings.screenHeight;
import static casadodocodigo.bis.config.DeviceSettings.screenResolution;
import static casadodocodigo.bis.config.DeviceSettings.screenWidth;

public class MenuButtons extends CCLayer implements ButtonDelegate {

    private Button playButton;
    private Button highscoredButton;
    private Button helpButton;
    private Button soundButton;

    public MenuButtons() {
        this.setIsTouchEnabled(true);

        this.playButton = new Button(Assets.PLAY);
        this.highscoredButton = new Button(Assets.HIGHSCORE);
        this.helpButton = new Button(Assets.HELP);
        this.soundButton = new Button(Assets.SOUND);

        setButtonsPosition();

        this.playButton.setDelegate(this);
        this.highscoredButton.setDelegate(this);
        this.helpButton.setDelegate(this);
        this.soundButton.setDelegate(this);

        addChild(playButton);
        addChild(highscoredButton);
        addChild(helpButton);
        addChild(soundButton);
    }

    private void setButtonsPosition() {
        playButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 250)));
        highscoredButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 300)));
        helpButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2, screenHeight() - 350)));
        soundButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2 - 100, screenHeight() - 420)));
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.playButton)) {
            System.out.println("Button clicked: play");
            CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1.0F, GameScene.createGame()));
        }
        if (sender.equals(this.highscoredButton)) {
            System.out.println("Button clicked: highscore");
        }
        if (sender.equals(this.helpButton)) {
            System.out.println("Button clicked: help");
        }
        if (sender.equals(this.soundButton)) {
            System.out.println("Button clicked: sound");
        }

    }
}
