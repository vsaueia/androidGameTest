package casadodocodigo.bis.cenas;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import casadodocodigo.bis.R;
import casadodocodigo.bis.config.Assets;
import casadodocodigo.bis.controle.PauseDelegate;
import casadodocodigo.bis.controle.Runner;
import casadodocodigo.bis.telas.GameButtons;
import casadodocodigo.bis.controle.MeteorsEngine;
import casadodocodigo.bis.controle.MeteorsEngineDelegate;
import casadodocodigo.bis.controle.ScreenBackground;
import casadodocodigo.bis.controle.ShootEngineDelegate;
import casadodocodigo.bis.objetos.Meteor;
import casadodocodigo.bis.objetos.Player;
import casadodocodigo.bis.objetos.Score;
import casadodocodigo.bis.objetos.Shoot;
import casadodocodigo.bis.telas.FinalScreen;
import casadodocodigo.bis.telas.GameOverScreen;
import casadodocodigo.bis.telas.PauseScreen;
import casadodocodigo.bis.telas.TitleScreen;

import static casadodocodigo.bis.config.DeviceSettings.screenHeight;
import static casadodocodigo.bis.config.DeviceSettings.screenResolution;
import static casadodocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by viniciussilva on 06/10/2016.
 */

public class GameScene extends CCLayer implements MeteorsEngineDelegate, ShootEngineDelegate, PauseDelegate {
    private ScreenBackground background;
    private MeteorsEngine meteorsEngine;
    private CCLayer meteorsLayer;
    private List meteorsArray;
    private CCLayer playerLayer;
    private Player player;
    private CCLayer shootsLayer;
    private ArrayList shootsArray;
    private List playersArray;
    private CCLayer scoreLayer;
    private Score score;
    private PauseScreen pauseScreen;
    private CCLayer layerTop;

    private GameScene() {
        this.setIsTouchEnabled(true);

        preloadCache();

        SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), R.raw.music, true);

        GameButtons gameButtonsLayer = GameButtons.gameButtons();
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(screenResolution(
                CGPoint.ccp(screenWidth() / 2.0F, screenHeight() / 2.0F)));
        this.addChild(this.background);

        this.meteorsLayer = CCLayer.node();
        this.addChild(this.meteorsLayer);

        this.playerLayer = CCLayer.node();
        this.addChild(this.playerLayer);

        gameButtonsLayer.setDelegate(this);
        this.addChild(gameButtonsLayer);

        this.shootsLayer = CCLayer.node();
        this.addChild(this.shootsLayer);

        this.scoreLayer = CCLayer.node();
        this.addChild(this.scoreLayer);

        this.layerTop = CCLayer.node();
        this.addChild(this.layerTop);

        this.addGameObjects();
    }

    public void startGame() {
        player.catchAccelerometer();
    }

    private void preloadCache() {
        SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.shoot);
        SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.bang);
        SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.over);

    }

    public static CCScene createGame() {
        CCScene scene = CCScene.node();
        GameScene layer = new GameScene();
        scene.addChild(layer);
        return scene;
    }

    @Override
    public void createMeteor(Meteor meteor, float x, float y, float vel, double ang, int vl) {
        meteor.setDelegate(this);
        this.meteorsLayer.addChild(meteor);
        meteor.start();
        this.meteorsArray.add(meteor);
    }

    @Override
    public void createMeteor(Meteor meteor) {
        meteor.setDelegate(this);
        this.meteorsLayer.addChild(meteor);
        meteor.start();
        this.meteorsArray.add(meteor);
    }

    @Override
    public void removeMeteor(Meteor meteor) {
        this.meteorsArray.remove(meteor);
    }


    private void addGameObjects() {
        this.meteorsArray = new ArrayList();
        this.meteorsEngine = new MeteorsEngine();

        this.player = new Player();
        this.player.setDelegate(this);
        this.playerLayer.addChild(this.player);
        this.playersArray = new ArrayList();
        this.playersArray.add(this.player);

        this.shootsArray = new ArrayList();

        this.score = new Score();
        this.score.setDelegate(this);
        this.scoreLayer.addChild(this.score);
    }

    @Override
    public void onEnter() {
        super.onEnter();

        Runner.check().setIsGamePlaying(true);
        Runner.check().setIsGamePaused(false);

        SoundEngine.sharedEngine().setEffectsVolume(1F);
        SoundEngine.sharedEngine().setSoundVolume(1F);

        this.schedule("checkHits");
        this.startGame();

        this.startEngines();
    }

    public void startFinalScreen() {
        CCDirector.sharedDirector().replaceScene(new FinalScreen().scene());
    }
    private void startEngines() {
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }

    @Override
    public void createShoot(Shoot shoot) {
        this.shootsLayer.addChild(shoot);
        shoot.setDelegate(this);
        shoot.start();
        this.shootsArray.add(shoot);
    }

    @Override
    public void removeShoot(Shoot shoot) {
        this.shootsArray.remove(shoot);
    }

    public boolean shoot() {
        player.shoot();
        return true;
    }

    public void moveLeft() {
        player.moveLeft();
    }

    public void moveRight() {
        player.moveRight();
    }

    public void meteorHit(CCSprite meteor, CCSprite shoot) {
        ((Meteor) meteor).shooted();
        ((Shoot) shoot).explode();
        this.score.increase();
    }

    public void playerHit(CCSprite meteor, CCSprite player) {
        ((Meteor) meteor).shooted();
        ((Player) player).explode();
        CCDirector.sharedDirector().replaceScene(new GameOverScreen().scene());
    }

    public CGRect getBorders(CCSprite object) {
        CGRect rect = object.getBoundingBox();
        CGPoint glPoint = rect.origin;
        CGRect glRect = CGRect.make(glPoint.x, glPoint.y, rect.size.width, rect.size.height);
        return glRect;
    }

    public void checkHits(float dt) {
        this.checkRadiusHitsOfArray(this.meteorsArray, this.shootsArray, this, "meteorHit");
        this.checkRadiusHitsOfArray(this.meteorsArray, this.playersArray, this, "playerHit");
    }

    private boolean checkRadiusHitsOfArray(List<? extends CCSprite> array1, List<? extends CCSprite> array2, GameScene gameScene, String hit) {
        boolean result = false;

        for(int i = 0; i < array1.size(); i++) {
            CGRect rect1 = getBorders(array1.get(i));

            for(int j = 0; j < array2.size(); j++) {
                CGRect rect2 = getBorders(array2.get(j));

                if (CGRect.intersects(rect1, rect2)) {
                    System.out.println("Colision detected: " + hit);
                    result = true;

                    Method method;
                    try {
                        method = GameScene.class.getMethod(hit, CCSprite.class, CCSprite.class);
                        method.invoke(gameScene, array1.get(i), array2.get(j));
                    } catch (SecurityException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    private void pauseGame() {
        if (!Runner.check().isGamePaused() && Runner.check().isGamePlaying()) {
            Runner.setIsGamePaused(true);
        }
    }

    public void resumeGame() {
        if (Runner.check().isGamePaused() || !Runner.check().isGamePlaying()) {
            this.pauseScreen = null;
            Runner.setIsGamePaused(false);
            Runner.setIsGamePlaying(true);
        }
    }

    public void quitGame() {
        SoundEngine.sharedEngine().setEffectsVolume(0F);
        SoundEngine.sharedEngine().setSoundVolume(0F);

        CCDirector.sharedDirector().replaceScene(new TitleScreen().scene());
    }

    public void pauseGameAndShowLayer() {
        if (Runner.check().isGamePlaying() && !Runner.isGamePaused()) {
            this.pauseGame();
        }

        if (Runner.check().isGamePaused() && Runner.check().isGamePlaying() && this.pauseScreen == null) {
            this.pauseScreen = new PauseScreen();
            this.layerTop.addChild(this.pauseScreen);
            this.pauseScreen.setDelegate(this);
        }


    }
}
