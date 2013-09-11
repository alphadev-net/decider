package net.alphaDev.Decider.Actions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class ShakeAction
        implements SensorEventListener {

	/** Minimum movement force to consider. */
	private static final int MIN_FORCE = 10;

	/**
	 * Minimum times in a shake gesture that the direction of movement needs to
	 * change.
	 */
	private static final int MIN_DIRECTION_CHANGE = 3;

	/** Maximum pause between movements. */
	private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;

	/** Maximum allowed time for shake gesture. */
	private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;

	/** Time when the gesture started. */
	private long mFirstDirectionChangeTime = 0;

	/** Time when the last movement started. */
	private long mLastDirectionChangeTime;

	/** How many movements are considered so far. */
	private int mDirectionChangeCount = 0;

	/** The last x position. */
	private float lastX = 0;

	/** The last y position. */
	private float lastY = 0;

	/** The last z position. */
	private float lastZ = 0;

    private OnShakeListener mListener;
    private SensorManager mSensorManager;

    public ShakeAction(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mListener = listener;
    }

	public void register(OnShakeListener listener) {
		mListener = listener;
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor != null) {
		    mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
	}

	public void unregister() {
		mSensorManager.unregisterListener(this);
	}

    @Override
    public void onSensorChanged(SensorEvent se) {
		// get sensor data
		float x = se.values[SensorManager.DATA_X];
		float y = se.values[SensorManager.DATA_Y];
		float z = se.values[SensorManager.DATA_Z];

		// calculate movement
		float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

		if (totalMovement > MIN_FORCE) {

			// get time
			long now = System.currentTimeMillis();

			// store first movement time
			if (mFirstDirectionChangeTime == 0) {
				mFirstDirectionChangeTime = now;
				mLastDirectionChangeTime = now;
			}

			// check if the last movement was not long ago
			long lastChangeWasAgo = now - mLastDirectionChangeTime;
			if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {

				// store movement data
				mLastDirectionChangeTime = now;
				mDirectionChangeCount++;

				// store last sensor data 
				lastX = x;
				lastY = y;
				lastZ = z;

				// check how many movements are so far
				if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {

					// check total duration
					long totalDuration = now - mFirstDirectionChangeTime;
					if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
						if(mListener != null) {
							mListener.shake();
						}

						resetShakeParameters();
					}
				}

			} else {
				resetShakeParameters();
			}
		}
	}

	/**
	 * Resets the shake parameters to their default values.
	 */
	private void resetShakeParameters() {
		mFirstDirectionChangeTime = 0;
		mDirectionChangeCount = 0;
		mLastDirectionChangeTime = 0;
		lastX = 0;
		lastY = 0;
		lastZ = 0;
	}

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public static interface OnShakeListener {
        public void shake();
    }
}
