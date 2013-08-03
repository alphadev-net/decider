package net.alphaDev.Decider.Actions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class ShakeAction
        implements SensorEventListener {

    private static final int sFlags = SensorManager.SENSOR_ACCELEROMETER;

    private OnShakeListener mListener;
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    public ShakeAction(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    public ShakeAction(Context context, OnShakeListener listener) {
        this(context);
        setOnShakeListener(listener);
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if (mAccel > 8 && mListener != null) {
            mListener.shake();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public static interface OnShakeListener {
        public void shake();
    }
}
