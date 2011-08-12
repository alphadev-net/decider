package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Util.Utility;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author jan
 */
public class addAction implements OnClickListener {
    private final Activity caller;

    public addAction(Activity caller) {
        this.caller = caller;
    }

    public void onClick(View view) {
        decideListAdapter adapter = (decideListAdapter) Utility.extractAdapter(caller);
        TextView text = (TextView) caller.findViewById(R.id.addlabel);
        String label = text.getText().toString();

        if(label.length() > 0) {
            text.setText(null);
            adapter.add(label);
        }
    }
}