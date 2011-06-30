package net.alphaDev.Decider;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 *
 * @author jan
 */
public class addAction implements OnClickListener {
    Activity caller;

    addAction(Activity caller) {
        this.caller = caller;
    }

    public void onClick(View view) {
        decideListAdapter adapter = (decideListAdapter) ((Decider)caller).getListAdapter();
        TextView text = (TextView) caller.findViewById(R.id.addlabel);
        String label = text.getText().toString();
        text.setText(null);
        
        adapter.add(new listItem(caller, label));
    }
}