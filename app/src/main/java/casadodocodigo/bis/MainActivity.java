package casadodocodigo.bis;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import casadodocodigo.bis.config.DeviceSettings;
import casadodocodigo.bis.telas.TitleScreen;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CCGLSurfaceView ccglSurfaceView = new CCGLSurfaceView(this);
        setContentView(ccglSurfaceView);
        CCDirector.sharedDirector().attachInView(ccglSurfaceView);

        configSensormanager();

        CCDirector.sharedDirector().setScreenSize(320, 480);

        CCScene scene = new TitleScreen().scene();
        CCDirector.sharedDirector().runWithScene(scene);

    }

    private void configSensormanager() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        DeviceSettings.setSensorManager(sensorManager);
    }
}
