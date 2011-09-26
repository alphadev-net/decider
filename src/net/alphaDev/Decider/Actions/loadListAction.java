package net.alphaDev.Decider.Actions;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import net.alphaDev.Decider.Decider;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class loadListAction implements OnItemClickListener {
    private final Decider caller;
    private final Dialog parent;

    public loadListAction(Decider caller, Dialog parent) {
        this.caller = caller;
        this.parent = parent;
    }

    public void onItemClick(AdapterView<?> dialogAdapter, View selectedView, int position, long row) {
        caller.setAdapter(caller.getDatabase().readList((int)row));
        parent.dismiss();
    }
}