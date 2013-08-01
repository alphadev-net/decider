package net.alphaDev.Decider;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.R;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderActivity
		extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

	// Setup DialogIDs
	public static final int DIALOG_ABOUT_ID = 0;
	public static final int DIALOG_SAVE_ID = 1;
	public static final int DIALOG_LOAD_ID = 2;
	public static final int DIALOG_ADD_ID = 4;
	public static final int DIALOG_EDIT_ID = 8;

	// Datasources (flagged static for synchronized access)
	private DecideListAdapter mAdapter;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Load UI layout from XML
		setContentView(R.layout.main);

		mAdapter = new DecideListAdapter(null);
		setListAdapter(mAdapter);

		final LoaderManager manager = getLoaderManager();
		manager.initLoader(0, Bundle.EMPTY, this);
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

	private int pickNumberLowerThan(int max) {
		return (int) (Math.floor(Math.random() * max));
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		CursorLoader loader = new CursorLoader(this);
		loader.setUri(null);
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
