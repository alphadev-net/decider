package net.alphaDev.Decider;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import net.alphaDev.Decider.Actions.ShakeAction;
import net.alphaDev.Decider.Fragments.DecideFragment;
import net.alphaDev.Decider.Fragments.DeciderListFragment;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideActivity
        extends Activity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);

        if (icicle == null) {
            Fragment mFragment = new DecideFragment();
            mFragment.setRetainInstance(true);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(android.R.id.content, mFragment).commit();
        }
    }
}
