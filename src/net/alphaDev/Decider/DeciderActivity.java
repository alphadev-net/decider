package net.alphaDev.Decider;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.Fragments.ItemFragment;
import net.alphaDev.Decider.Fragments.LoadListFragment;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.R;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderActivity
		extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

	// Datasources (flagged static for synchronized access)
	private DecideListAdapter mAdapter;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Load UI layout from XML
		setContentView(R.layout.main);

		mAdapter = new DecideListAdapter(null);
		setListAdapter(mAdapter);
	}

	// Provide OptionsMenu from XML
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.decider_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem editItem = menu.findItem(R.id.edit_btn);
		editItem.setEnabled(mAdapter.getCount() > 0);

		MenuItem saveItem = menu.findItem(R.id.save_btn);
		saveItem.setEnabled(mAdapter.getCount() > 0);

		MenuItem clearItem = menu.findItem(R.id.clear_btn);
		clearItem.setEnabled(mAdapter.getCount() > 0);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelectes(MenuItem item) {
		DialogFragment fragment = null;

		switch(item.getItemId()) {
			case R.id.add_btn:
			    fragment = new ItemFragment();
				break;
			case R.id.load_btn:
			    fragment = new LoadListFragment(this);
				break;
		}

		if(fragment != null) {
			final FragmentManager manager = getFragmentManager();
			fragment.show(manager, "dialog");
		}

		return super.onOptionsItemSelected(item);
	}

	private int pickNumberLowerThan(int max) {
		return (int) (Math.floor(Math.random() * max));
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		CursorLoader loader = new CursorLoader(this);
		final Uri listId = bundle.getParcelable("asdf");
		loader.setUri(listId);
		loader.setProjection(Item.DEFAULT_PROJECTION);
		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> p1) {
		mAdapter.swapCursor(null);
	}
}
