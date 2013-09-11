package net.alphaDev.Decider.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.alphaDev.Decider.Actions.ShakeAction;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Util.Constants;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideFragment
        extends Fragment
        implements ShakeAction.OnShakeListener {

    private Item[] mItems;
    private TextView resultView;
    private ShakeAction mShakeTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.decider_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Bundle extras = getArguments();
        if(extras != null) {
            Parcelable[] parcels = extras.getParcelableArray(Constants.LIST_PARAMETER);
            mItems = new Item[parcels.length];
            for(int i=0; i<parcels.length; i++) {
                mItems[i] = (Item) parcels[i];
            }
        }

        resultView = (TextView) view.findViewById(R.id.resultView);
        decideAction();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mShakeTracker = new ShakeAction(getActivity());
    }

	@Override
	public void onResume() {
		super.onResume();
		mShakeTracker.register(this);
	}

	@Override
	public void onPause() {
		mShakeTracker.unregister();
		super.onPause();
	}

    private void decideAction() {
        CharSequence randomItem = getRandomItemLabel();
        resultView.setText(randomItem);
    }

    private CharSequence getRandomItemLabel() {
        int chosen = pickNumberLowerThan(mItems.length);
        return mItems[chosen].getLabel();
    }

    private int pickNumberLowerThan(int max) {
        return (int) (Math.floor(Math.random() * max));
    }

    @Override
    public void shake() {
        decideAction();
    }
}
