package net.alphaDev.Decider.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import net.alphaDev.Decider.Actions.DialogCancelledAction;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.DeciderListActivity;
import net.alphaDev.Decider.Model.List;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Util.Constants;
import net.alphaDev.Decider.Util.UriBuilder;
import net.alphaDev.Decider.Controllers.ItemController;
import net.alphaDev.Decider.Model.Item;
import android.os.RemoteException;
import net.alphaDev.Decider.Controllers.ListController;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class SaveListFragment
        extends DialogFragment
        implements DialogInterface.OnClickListener {

    private DecideListAdapter mAdapter;
    private Context mContext;
    private TextView mText;
    private List mList;

    public SaveListFragment(DeciderListActivity mContext) {
        this.mContext = mContext;
        this.mAdapter = (DecideListAdapter) mContext.getListFragment().getListAdapter();
        this.mList = mAdapter.getList();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        mText = (TextView) inflater.inflate(R.layout.save_dialog, null, false);

		if(mList != null) {
			mText.setText(mList.getLabel());
		}

        return new AlertDialog.Builder(mContext)
                .setTitle(R.string.list_title_dialog_message)
                .setView(mText)
                .setCancelable(true)
				.setNegativeButton(R.string.cancel, new DialogCancelledAction())
                .setPositiveButton(R.string.save_btn, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int btnId) {
        CharSequence label = mText.getText();
        if(label.length() == 0) {
            Toast.makeText(mContext, R.string.empty_save, Toast.LENGTH_SHORT).show();
        } else {
            if(mList == null) {
				mList = new List(mText.getText());
			}
			
			try {
				ListController.save(mContext, mList);
			} catch (RemoteException e) {}

			for(int i = 0; i < mAdapter.getCount(); i++) {
				Item item = (Item) mAdapter.getItem(i);
				item.setList(mList.getId());
				if(!item.isSaved() || !mList.getLabel().equals(mText.getText())) {
					try {
						ItemController.save(mContext, item);
					} catch (RemoteException e) {}
				} else {
					try {
						ItemController.update(mContext, item);
					} catch (RemoteException e) {}
				}
			}
            dialogInterface.dismiss();
        }
    }
}
