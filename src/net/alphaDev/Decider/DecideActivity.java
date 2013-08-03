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
