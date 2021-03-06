package net.alphaDev.Decider.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.alphaDev.Decider.Actions.DialogCancelledAction;
import net.alphaDev.Decider.Adapter.LoadListAdapter;
import net.alphaDev.Decider.Controllers.ListController;
import net.alphaDev.Decider.DeciderListActivity;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Util.Constants;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class LoadListFragment
        extends DialogFragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        ListView.OnItemClickListener {

	private DeciderListActivity mContext;
	private LoadListAdapter mAdapter;
	private LoaderManager loaderManager;

    public LoadListFragment(DeciderListActivity ctx) {
		mContext = ctx;
		loaderManager = ctx.getLoaderManager();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ListView view = new ListView(mContext);
		view.setAdapter(mAdapter =  new LoadListAdapter(mContext));
		view.setOnItemClickListener(this);
		loaderManager.initLoader(-1, Bundle.EMPTY, this);
		return new AlertDialog.Builder(mContext)
		.setTitle(R.string.load_title_dialog_message)
		.setView(view)
		.setCancelable(true)
		.setNegativeButton(R.string.cancel, new DialogCancelledAction())
		.create();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
		Bundle bundle = new Bundle(1);
		bundle.putLong(Constants.LIST_PARAMETER, id);
		loaderManager.initLoader(pos, bundle, mContext.getListFragment());
		dismissAllowingStateLoss();
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return ListController.getLists(mContext);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	    mAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
