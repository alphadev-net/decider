package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import net.alphaDev.Decider.ITitle;
import net.alphaDev.Decider.Util.Utility;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class saveListAction implements OnClickListener {
    private final Activity caller;
    private EditText input;

    public saveListAction(Activity caller, EditText input) {
        this.caller = caller;
        this.input = input;
    }

    public void onClick(DialogInterface di, int i) {
        ITitle dataSource = (decideListAdapter) Utility.extractAdapter(caller);
        dataSource.setTitle(input.getText().toString());
        di.dismiss();
    }
}