package casadodocodigo.bis.objetos;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import casadodocodigo.bis.R;
import casadodocodigo.bis.config.Assets;
import casadodocodigo.bis.controle.Accelerometer;
import casadodocodigo.bis.controle.AccelerometerDelegate;
import casadodocodigo.bis.controle.ShootEngineDelegate;

import static casadodocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by viniciussilva on 06/10/2016.
 */

public class Player extends CCSprite implements AccelerometerDelegate {
    float positionX = screenWidth() / 2;
    float positionY = 50;
    private ShootEngineDelegate delegate;
    private float currentAccelX;
    private float currentAccelY;
    private Accelerometer accelerometer;
    private static final double NOISE = 1;

    public Player() {
        super(Assets.NAVE);
        setPosition(positionX, positionY);
    }

    public void shoot() {
        delegate.createShoot(new Shoot(positionX, positionY));
    }

    public void setDelegate(ShootEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public void moveLeft() {
        if (positionX > 30) {
            positionX -= 10;
        }
        setPosition(positionX, positionY);
    }

    public void moveRight() {
        if (positionX < screenWidth() - 30) {
            positionX += 10;
        }
        setPosition(positionX, positionY);
    }

    public void explode() {
        SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.over);
        SoundEngine.sharedEngine().pauseSound();

        this.unschedule("update");

        float dt = 0.2F;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.2F);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);

        this.runAction(CCSequence.actions(s1));
    }

    @Override
    public void accelerometerDidAccelerate(float x, float y) {
        System.out.println("X: " + x);
        System.out.println("Y: " + y);

        this.currentAccelX = x;
        this.currentAccelY = y;
    }

    public void catchAccelerometer() {
        Accelerometer.sharedAccelerometer().catchAccelerometer();
        this.accelerometer = Accelerometer.sharedAccelerometer();
        this.accelerometer.setDelegate(this);
    }

    public void update(float dt) {
        if (this.currentAccelX < -NOISE) {
            this.positionX++;
        }

        if (this.currentAccelX > NOISE) {
            this.positionX--;
        }

        if (this.currentAccelY < -NOISE) {
            this.positionY++;
        }

        if (this.currentAccelY > NOISE) {
            this.positionY--;
        }

        this.setPosition(CGPoint.ccp(this.positionX, this.positionY));
    }
}
