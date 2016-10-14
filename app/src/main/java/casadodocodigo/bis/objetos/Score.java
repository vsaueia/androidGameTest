package casadodocodigo.bis.objetos;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;

import casadodocodigo.bis.cenas.GameScene;

import static casadodocodigo.bis.config.DeviceSettings.screenHeight;
import static casadodocodigo.bis.config.DeviceSettings.screenWidth;

/**
 * Created by viniciussilva on 13/10/2016.
 */

public class Score extends CCLayer {
    private int score;

    private CCBitmapFontAtlas text;

    private GameScene delegate;

    public Score() {
        this.score = 0;

        this.text = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(this.score), "UniSansSemiBold_Numbers_240.fnt");

        this.text.setScale((float) 240 / 240);

        this.setPosition(screenWidth() - 50, screenHeight() - 50);
        this.addChild(this.text);
    }

    public void increase() {
        score++;
        this.text.setString(String.valueOf(this.score));
        if (score == 1) {
            this.delegate.startFinalScreen();
        }
    }
}
