package net.alphaDev.Decider.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.widget.ListAdapter;
import android.widget.Toast;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideFragment
        extends Fragment {

	private Context mContext;

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
