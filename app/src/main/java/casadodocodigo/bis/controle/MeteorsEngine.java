package casadodocodigo.bis.controle;

import org.cocos2d.layers.CCLayer;

import java.util.Random;

import casadodocodigo.bis.config.Assets;
import casadodocodigo.bis.objetos.Meteor;

public class MeteorsEngine extends CCLayer {
    private MeteorsEngineDelegate delegate;

    public MeteorsEngine() {
        this.schedule("meteorsEngine", 1.0F / 10F);
    }

    public void meteorsEngine(float dt) {
        if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
            if (new Random().nextInt(30) == 0) {
                this.getDelegate().createMeteor(new Meteor(Assets.METEOR));
            }
        }
    }

    public MeteorsEngineDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(MeteorsEngineDelegate delegate) {
        this.delegate = delegate;
    }
}
