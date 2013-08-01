package net.alphaDev.Decider.Fragments;

import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.DeciderActivity;
import net.alphaDev.Decider.Model.List;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import net.alphaDev.Decider.Util.UriBuilder;

public class LoadListFragment
        extends DialogFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

	private DeciderActivity mContext;
	private DecideListAdapter mAdapter;

    public LoadListFragment(DeciderActivity ctx) {
		mContext = ctx;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
		return new ListView(mContext);
	}

	@Override
	public void onViewCreated(View view, Bundle icicle) {
		super.onViewCreated(view, icicle);
		ListView list = (ListView) view;
		list.setAdapter(mAdapter =  new DecideListAdapter(mContext, List.Columns.LABEL));

		final LoaderManager manager = mContext.getLoaderManager();
		manager.initLoader(-1, Bundle.EMPTY, this);
	}
	
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		CursorLoader loader = new CursorLoader(mContext);
		loader.setUri(UriBuilder.getListUri());
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
