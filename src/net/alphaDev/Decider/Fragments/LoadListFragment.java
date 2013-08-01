package net.alphaDev.Decider.Fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.DeciderActivity;
import net.alphaDev.Decider.Model.List;

public class LoadListFragment
        extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

	private DeciderActivity mContext;
	private DecideListAdapter mAdapter;

    public LoadListFragment(DeciderActivity ctx) {
		mContext = ctx;
		setListAdapter(mAdapter = new DecideListAdapter(ctx));
		final LoaderManager manager = ctx.getLoaderManager();
		manager.initLoader(-1, Bundle.EMPTY, this);
	}


	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		CursorLoader loader = new CursorLoader(mContext);
		loader.setUri(null);
		loader.setProjection(List.DEFAULT_PROJECTION);
		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	    mAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
