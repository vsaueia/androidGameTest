package casadodocodigo.bis.controle;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import casadodocodigo.bis.config.DeviceSettings;

/**
 * Created by viniciussilva on 14/10/2016.
 */

public class Accelerometer implements SensorEventListener {

    private float currentAccelerationX;
    private float currentAccelerationY;
    private AccelerometerDelegate delegate;
    private SensorManager sensorManager;
    private float calibratedAccelerationX;
    private float calibratedAccelerationY;

    private int calibrated;

    private Accelerometer() {
        this.catchAccelerometer();
    }

    public void catchAccelerometer() {
        sensorManager = DeviceSettings.getSensorManager();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    public static Accelerometer sharedAccelerometer() {
        if (sharedAccelerometer == null) {
            sharedAccelerometer = new Accelerometer();
        }
        return sharedAccelerometer;
    }

    static Accelerometer sharedAccelerometer = null;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (calibrated < 100) {
            this.calibratedAccelerationX += event.values[0];
            this.calibratedAccelerationY += event.values[1];

            System.out.println(event.values[0]);
            System.out.println(event.values[1]);

            calibrated++;

            if (calibrated == 100) {
                this.calibratedAccelerationX /= 100;
                this.calibratedAccelerationY /= 100;
            }
            return;
        }

        this.currentAccelerationX = event.values[0] - this.calibratedAccelerationX;
        this.currentAccelerationY = event.values[1] - this.calibratedAccelerationY;

        if (this.delegate != null) {
            this.delegate.accelerometerDidAccelerate(currentAccelerationX, currentAccelerationY);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setDelegate(AccelerometerDelegate delegate) {
        this.delegate = delegate;
    }

    public AccelerometerDelegate getDelegate() {
        return delegate;
    }
}
