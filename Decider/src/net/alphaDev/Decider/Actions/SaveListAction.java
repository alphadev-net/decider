package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import net.alphaDev.Decider.Decider;
import net.alphaDev.Decider.Storage.DeciderStorage;
import net.alphaDev.Decider.Util.Utility;
import net.alphaDev.Decider.DecideListAdapter;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class SaveListAction implements OnClickListener {
    private final Activity caller;
    private EditText input;

    public SaveListAction(Activity caller, EditText input) {
        this.caller = caller;
        this.input = input;
    }

    public void onClick(DialogInterface di, int i) {
        DecideListAdapter dataSource = (DecideListAdapter) Utility.extractAdapter(caller);
        DeciderStorage mDB = ((Decider) caller).getDatabase();
        mDB.writeList(input.getText().toString(), dataSource);

        di.dismiss();
    }
}