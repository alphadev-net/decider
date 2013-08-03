package net.alphaDev.Decider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.Toast;

import net.alphaDev.Decider.R;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideActivity
        extends Activity {

	private CharSequence[] mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.decider_dialog);
        mItems = getIntent().getExtras().getCharSequenceArray("list");
    }

    private void decideAction() {
		CharSequence randomItem = getRandomItemLabel();
		Toast.makeText(this, randomItem, Toast.LENGTH_SHORT);
	}

	private CharSequence getRandomItemLabel() {
		int chosen = pickNumberLowerThan(mItems.length);
		return mItems[chosen];
	}

	private int pickNumberLowerThan(int max) {
		return (int) (Math.floor(Math.random() * max));
	}
}
