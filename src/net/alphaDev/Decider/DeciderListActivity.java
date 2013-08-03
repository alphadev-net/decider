package net.alphaDev.Decider;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
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
import net.alphaDev.Decider.Controllers.ItemController;
import net.alphaDev.Decider.Fragments.AboutFragment;
import net.alphaDev.Decider.Fragments.ItemFragment;
import net.alphaDev.Decider.Fragments.LoadListFragment;
import net.alphaDev.Decider.Fragments.SaveListFragment;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.Util.UriBuilder;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderListActivity
		extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        ListView.OnItemLongClickListener,
		ActionMode.Callback {

	private DecideListAdapter mAdapter;
	private ActionMode mActionMode;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		setListAdapter(mAdapter = new DecideListAdapter(this));
		//getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
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
		validateMenuItem(menu, R.id.save_btn, hasItems);
		validateMenuItem(menu, R.id.clear_btn, hasItems);
		return super.onPrepareOptionsMenu(menu);
	}

	private void validateMenuItem(Menu menu, int itemRes, boolean enabled) {
		MenuItem item = menu.findItem(itemRes);
		item.setEnabled(enabled);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DialogFragment fragment = null;

		switch(item.getItemId()) {
			case R.id.add_btn:
			    fragment = new ItemFragment(this);
				break;
			case R.id.load_btn:
			    fragment = new LoadListFragment(this);
				break;
			case R.id.about_btn:
			    fragment = new AboutFragment();
				break;
            case R.id.save_btn:
                fragment = new SaveListFragment(this);
                break;
            case R.id.clear_btn:
                mAdapter.resetEntries();
                break;
			case R.id.decide_btn:
                Intent intent = new Intent(this, DecideActivity.class);
			    startActivity(intent);
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
		view.setSelected(true);
		return true;
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        final long listId = bundle.getLong("list");
		return ItemController.getItemsByListId(this, listId);
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
}
