package net.alphaDev.Decider.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import net.alphaDev.Decider.Actions.DialogCancelledAction;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.DeciderActivity;
import net.alphaDev.Decider.R;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class ItemFragment
        extends DialogFragment
        implements DialogInterface.OnClickListener {

    private long id;
    private Context mContext;
    private DecideListAdapter mAdapter;
    private TextView mText;

    public ItemFragment(DeciderActivity context) {
        mContext = context;
        mAdapter = (DecideListAdapter) context.getListAdapter();
    }

    public ItemFragment(DeciderActivity context, long id) {
        this(context);
        this.id = id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        mText = (TextView) inflater.inflate(R.layout.save_dialog, null, false);
        return new AlertDialog.Builder(mContext)
            .setTitle(R.string.add_title_dialog_message)
            .setView(mText)
            .setCancelable(true)
            .setNegativeButton(R.string.cancel, new DialogCancelledAction())
            .setPositiveButton(R.string.add_btn, this)
            .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        CharSequence newItem = mText.getText();
        if(newItem != null && newItem.length() > 0) {
            mAdapter.addEntry(newItem);
        }
        dialogInterface.dismiss();
    }
}
