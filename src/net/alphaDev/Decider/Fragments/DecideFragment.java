package net.alphaDev.Decider.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.alphaDev.Decider.Actions.ShakeAction;
import net.alphaDev.Decider.R;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideFragment
        extends Fragment
        implements ShakeAction.OnShakeListener {

    private CharSequence[] mItems;
    private TextView resultView;
    private ShakeAction mShakeTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.decider_dialog, container, true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Bundle extras = getArguments();
        if(extras != null) {
            mItems = extras.getCharSequenceArray("list");
        }

        resultView = (TextView) view.findViewById(R.id.resultView);
        decideAction();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mShakeTracker = new ShakeAction(getActivity());
        mShakeTracker.register(this);
    }

    @Override
    public void onDetach() {
        mShakeTracker.unregister();
        super.onDetach();
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
