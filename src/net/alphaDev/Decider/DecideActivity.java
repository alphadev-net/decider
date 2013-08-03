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

	private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.decider_dialog);
    }

    private void decideAction() {
		String randomItem = getRandomItemLabel(null/*adapter*/);
		Toast.makeText(mContext, randomItem, Toast.LENGTH_SHORT);
	}

	private String getRandomItemLabel(ListAdapter adapter) {
		int chosen = pickNumberLowerThan(adapter.getCount());
		Object item = adapter.getItem(chosen);
		if(item != null)
			return item.toString();
		return null;
	}

	private int pickNumberLowerThan(int max) {
		return (int) (Math.floor(Math.random() * max));
	}
}
