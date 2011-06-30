package net.alphaDev.Decider.Actions;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import net.alphaDev.Decider.Decider;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author jan
 */
public class deleteAction implements OnClickListener{
    private Context context;

    public deleteAction(Context c) {
        this.context = c;
    }

    public void onClick(View view) {
        //((decideListAdapter) ((Decider)c).getListAdapter()).remove(view.getParent());
    }
}