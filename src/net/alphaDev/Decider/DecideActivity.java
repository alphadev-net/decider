package net.alphaDev.Decider;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import net.alphaDev.Decider.Actions.ShakeAction;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideActivity
        extends Activity
        implements ShakeAction.OnShakeListener {

	private CharSequence[] mItems;
    private TextView resultView;
	private ShakeAction mShakeTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.decider_dialog);

        final Bundle extras = getIntent().getExtras();
        if(extras != null) {
            mItems = extras.getCharSequenceArray("list");
        }

        resultView = (TextView) findViewById(R.id.resultView);
        decideAction();
		mShakeTracker = new ShakeAction(this);
    }

	@Override
	protected void onResume() {
		super.onResume();
		mShakeTracker.register();
	}

	@Override
	protected void onPause() {
		mShakeTracker.unregister();
		super.onPause();
	}

    private void decideAction() {
		CharSequence randomItem = getRandomItemLabel();
        resultView.setText(randomItem);
	}

	private CharSequence getRandomItemLabel() {
		int chosen = pickNumberLowerThan(mItems.length);
		return mItems[chosen];
	}

	private int pickNumberLowerThan(int max) {
		return (int) (Math.floor(Math.random() * max));
	}

    @Override
    public void shake() {
        decideAction();
    }
}
