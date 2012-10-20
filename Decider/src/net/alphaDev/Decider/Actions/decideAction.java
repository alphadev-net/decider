package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import kankan.wheel.widget.WheelView;
import net.alphaDev.Decider.Util.Utility;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class decideAction implements OnClickListener {
    private final Activity caller;

    public decideAction(Activity caller) {
        this.caller = caller;
    }

    public void onClick(View view) {
        decideListAdapter adapter = (decideListAdapter) Utility.extractAdapter(caller);

        int random = pickNumberLowerThan(adapter.getItemsCount());
        Log.i("Decider", "item: " + random);

        InputMethodManager mgr = (InputMethodManager) 
                caller.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        WheelView wheel = Utility.extractWheel(caller);
        //wheel.setCyclic(true);
        wheel.setCurrentItem(random, true);
        //wheel.setCyclic(false);
    }

    private int pickNumberLowerThan(int thisNumber) {
        return (int)(Math.floor(Math.random() * thisNumber));
    }
}