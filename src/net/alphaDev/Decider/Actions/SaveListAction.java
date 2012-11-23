package net.alphaDev.Decider.Actions;

import com.actionbarsherlock.app.SherlockActivity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import net.alphaDev.Decider.DeciderActivity;
import net.alphaDev.Decider.Storage.DeciderStorage;
import net.alphaDev.Decider.Util.Utility;
import net.alphaDev.Decider.DecideListAdapter;
import net.alphaDev.Decider.R;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class SaveListAction implements OnClickListener {
    private final SherlockActivity caller;

    public SaveListAction(SherlockActivity caller) {
        this.caller = caller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        final Dialog dialog = (Dialog) dialogInterface;
        final EditText input = (EditText) dialog.findViewById(R.id.DIALOG_SAVE_TEXT);

        final DeciderActivity activity = (DeciderActivity) caller;
        DecideListAdapter dataSource = (DecideListAdapter) Utility.extractAdapter(caller);
        DeciderStorage mDB = activity.getDatabase();
        mDB.writeList(input.getText().toString(), dataSource);

        dialogInterface.dismiss();
    }
}