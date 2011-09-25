package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import kankan.wheel.widget.WheelView;
import net.alphaDev.Decider.Util.Utility;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author jan
 */
public class decideAction implements OnClickListener {
    private final Activity caller;

    public decideAction(Activity caller) {
        this.caller = caller;
    }

    public void onClick(View view) {
        decideListAdapter adapter = (decideListAdapter) Utility.extractAdapter(caller);
        Logger logger = Logger.getLogger("Decider");

        int random = pickNumberLowerThan(adapter.getItemsCount());
        logger.log(Level.INFO, "item: {0}", random);

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