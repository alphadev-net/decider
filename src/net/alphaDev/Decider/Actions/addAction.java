package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import net.alphaDev.Decider.Decider;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.decideListAdapter;
import net.alphaDev.Decider.listItemFactory;

/**
 *
 * @author jan
 */
public class addAction implements OnClickListener {
    Activity caller;

    public addAction(Activity caller) {
        this.caller = caller;
    }

    public void onClick(View view) {
        decideListAdapter adapter = (decideListAdapter) ((Decider)caller).getListAdapter();
        TextView text = (TextView) caller.findViewById(R.id.addlabel);
        String label = text.getText().toString();
        text.setText(null);
        
        adapter.add(listItemFactory.create(caller, label));
    }
}