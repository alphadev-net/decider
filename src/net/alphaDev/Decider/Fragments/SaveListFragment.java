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

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class SaveListFragment
        extends DialogFragment
        implements DialogInterface.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private DecideListAdapter mAdapter;
    private Context mContext;
    private TextView mText;
    private List mList;
    private CharSequence mListLabel;

    public SaveListFragment(DeciderListActivity mContext) {
        this.mContext = mContext;
        this.mAdapter = (DecideListAdapter) mContext.getListFragment().getListAdapter();
        this.mList = mAdapter.getList();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            mText = (TextView) inflater.inflate(R.layout.save_dialog, null, false);
            return new AlertDialog.Builder(mContext)
                    .setTitle(R.string.list_title_dialog_message)
                    .setView(mText)
                    .setCancelable(true)
                    .setNegativeButton(R.string.cancel, new DialogCancelledAction())
                    .setPositiveButton(R.string.save_btn, this)
                    .create();
        } finally {
            long parent = mAdapter.getList().getId();
            if(parent != -1) {
                final Bundle bundle = new Bundle(1);
                bundle.putLong("list", parent);
                getLoaderManager().initLoader(-1, bundle, this);
            }
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        CharSequence label = mText.getText();
        if(label.length() == 0) {
            Toast.makeText(mContext, R.string.empty_save, Toast.LENGTH_SHORT).show();
        } else {
            if(mListLabel != label || -1 == -1) {
                // TODO: implement fresh list saving
            } else {
                // TODO: implement list updating
            }
            dialogInterface.dismiss();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        long parentList = bundle.getLong(Constants.LIST_PARAMETER);
        final CursorLoader loader = new CursorLoader(mContext);
        loader.setUri(UriBuilder.getListUri(parentList));
        loader.setProjection(List.DEFAULT_PROJECTION);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            int idIndex = cursor.getColumnIndex(List.Columns._ID);
            int labelIndex = cursor.getColumnIndex(List.Columns.LABEL);
            //mId = cursor.getLong(idIndex);
            mListLabel = cursor.getString(labelIndex);
            mText.setText(mListLabel);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }
}
