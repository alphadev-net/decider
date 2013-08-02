package net.alphaDev.Decider;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.Fragments.AboutFragment;
import net.alphaDev.Decider.Fragments.ItemFragment;
import net.alphaDev.Decider.Fragments.LoadListFragment;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Util.UriBuilder;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderActivity
		extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        ListView.OnItemLongClickListener,
        ListView.MultiChoiceModeListener {

	private DecideListAdapter mAdapter;
	private ActionMode mActionMode;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		setListAdapter(mAdapter = new DecideListAdapter(this));
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setOnItemLongClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.decider_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean hasItems = mAdapter.getCount() > 0;

		MenuItem saveItem = menu.findItem(R.id.save_btn);
		saveItem.setEnabled(hasItems);

		MenuItem clearItem = menu.findItem(R.id.clear_btn);
		clearItem.setEnabled(hasItems);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DialogFragment fragment = null;

		switch(item.getItemId()) {
			case R.id.add_btn:
			    fragment = new ItemFragment();
				break;
			case R.id.load_btn:
			    fragment = new LoadListFragment(this);
				break;
			case R.id.about_btn:
			    fragment = new AboutFragment();
				break;
			case R.id.decide_btn:
			    //fragment = new DecideFragment();
				break;
		}

		if(fragment != null) {
			fragment.show(getFragmentManager(), "dialog");
		}

		return super.onOptionsItemSelected(item);
	}

	public boolean onItemLongClick(AdapterView<?> p1, View view, int p3, long p4) {
		if(mActionMode != null) {
			return false;
		}

		// Start the CAB using the ActionMode.Callback defined above
		mActionMode = startActionMode(this);
		//view.setSelected(true);
		return true;
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		CursorLoader loader = new CursorLoader(this);
		final long listId = bundle.getLong("list");
		loader.setUri(UriBuilder.getItemUri());
		loader.setSelection(Item.Columns.LIST + " = ?");
		loader.setSelectionArgs(new String[]{
			Long.toString(listId)
		});
		loader.setProjection(Item.DEFAULT_PROJECTION);
		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> p1) {
		mAdapter.swapCursor(null);
	}

	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.item_menu, menu);
		return true;
	}

	public boolean onPrepareActionMode(ActionMode p1, Menu p2) {
		return false;
	}

	public boolean onActionItemClicked(ActionMode p1, MenuItem p2) {
		return false;
	}

	public void onDestroyActionMode(ActionMode p1) {
		mActionMode = null;
	}

	public void onItemCheckedStateChanged(ActionMode p1, int p2, long p3, boolean p4) {
	}
}
