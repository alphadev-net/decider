package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import net.alphaDev.Decider.Decider;
import net.alphaDev.Decider.Storage.deciderStorage;
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
        decideListAdapter dataSource = (decideListAdapter) Utility.extractAdapter(caller);
        deciderStorage mDB = ((Decider) caller).getDatabase();
        mDB.writeList(dataSource.getTitle(), dataSource);

        Toast.makeText(caller, "success", Toast.LENGTH_SHORT);
        di.dismiss();
    }
}