package net.alphaDev.Decider.Fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import net.alphaDev.Decider.R;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideFragment
        extends DialogFragment {

	private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.decider_dialog, container);
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
