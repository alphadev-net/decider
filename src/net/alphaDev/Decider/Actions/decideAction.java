package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.alphaDev.Decider.Decider;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Util.Utility;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author jan
 */
public class decideAction implements OnClickListener {
    Activity caller;

    public decideAction(Activity caller) {
        this.caller = caller;
    }

    public void onClick(View view) {
        decideListAdapter adapter = (decideListAdapter) Utility.extractAdapter(caller);
        int random = (int)Math.floor(Math.random()*adapter.getCount());

        TextView label = (TextView) ((LinearLayout) adapter.getItem(random)).findViewById(R.id.item);
        view.setTag(label);
        caller.showDialog(Decider.DIALOG_RESULT_ID);
    }
}