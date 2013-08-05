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
import net.alphaDev.Decider.DeciderListActivity;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.Util.Constants;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class ItemFragment
        extends DialogFragment
        implements DialogInterface.OnClickListener {

    private Item mItem;
    private Context mContext;
    private DecideListAdapter mAdapter;
    private TextView mText;

    public ItemFragment(DeciderListActivity context) {
        mContext = context;
        mAdapter = (DecideListAdapter) context.getListFragment().getListAdapter();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        mText = (TextView) inflater.inflate(R.layout.save_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		if(getArguments() != null) {
		    mItem = getArguments().getParcelable(Constants.ITEM_PARAMETER);
			mText.setText(mItem.getLabel());
		}

		if(mItem == null) {
            builder.setTitle(R.string.add_title_dialog_message)
			    .setPositiveButton(R.string.add_btn, this);
		} else {
			builder.setTitle(R.string.edit_title_dialog_message)
			    .setPositiveButton(R.string.edit_btn, this);
		}
        return builder.setView(mText)
            .setCancelable(true)
            .setNegativeButton(R.string.cancel, new DialogCancelledAction())
			.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        CharSequence newItem = mText.getText();
        if(newItem != null && newItem.length() > 0) {
			if(mItem == null) {
                mAdapter.addEntry(newItem);
			} else {
				mAdapter.updateEntry(mItem.getId(), newItem);
			}
        }
        dialogInterface.dismiss();
    }
}
