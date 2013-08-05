package net.alphaDev.Decider.Fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import net.alphaDev.Decider.Actions.ShakeAction;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.Controllers.ItemController;
import net.alphaDev.Decider.DecideActivity;
import net.alphaDev.Decider.DeciderListActivity;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.R;
import net.alphaDev.Decider.Util.Constants;
import net.alphaDev.Decider.Util.UriBuilder;
import android.os.RemoteException;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderListFragment
        extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        ListView.OnItemClickListener,
        ListView.OnItemLongClickListener,
        ActionMode.Callback,
        ShakeAction.OnShakeListener {

    private DecideListAdapter mAdapter;
    private ActionMode mActionMode;
    private DeciderListActivity mActivity;
    private ShakeAction mShakeTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (DeciderListActivity) getActivity();
        return inflater.inflate(R.layout.decider_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        setListAdapter(mAdapter = new DecideListAdapter(mActivity));

        final ListView list = getListView();
        if(list != null) {
            list.setOnItemLongClickListener(this);
            list.setOnItemClickListener(this);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mShakeTracker = new ShakeAction(getActivity());
    }

	@Override
	public void onResume() {
		super.onResume();
		mShakeTracker.register(this);
	}

	@Override
	public void onPause() {
		mShakeTracker.unregister();
		super.onPause();
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.decider_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        boolean hasItems = mAdapter.getCount() > 0;
        validateMenuItem(menu, R.id.save_btn, hasItems);
        validateMenuItem(menu, R.id.clear_btn, hasItems);
    }

    private void validateMenuItem(Menu menu, int itemRes, boolean enabled) {
        MenuItem item = menu.findItem(itemRes);
        if (item != null) {
            item.setEnabled(enabled);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DialogFragment fragment = null;

        switch(item.getItemId()) {
            case R.id.add_btn:
                fragment = new ItemFragment(mActivity);
                break;
            case R.id.load_btn:
                fragment = new LoadListFragment(mActivity);
                break;
            case R.id.about_btn:
                fragment = new AboutFragment();
                break;
            case R.id.save_btn:
                fragment = new SaveListFragment(mActivity);
                break;
            case R.id.clear_btn:
                mAdapter.resetEntries();
                break;
            case R.id.decide_btn:
                triggerDecider();
                break;
        }

        final FragmentManager manager = getFragmentManager();
        if(fragment != null && manager != null) {
            fragment.show(manager, "dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    private void triggerDecider() {
        Intent intent = new Intent(mActivity, DecideActivity.class);
        intent.putExtra("list", mAdapter.dumpItems());
        startActivity(intent);
    }

    public boolean onItemLongClick(AdapterView<?> p1, View view, int id, long p4) {
        if(mActionMode != null) {
            return false;
        }

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = mActivity.startActionMode(this);
        mAdapter.setSelected(id, true);
        return true;
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        final long listId = bundle.getLong(Constants.LIST_PARAMETER);
        return ItemController.getItemsByListId(mActivity, listId);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> p1) {
        mAdapter.swapCursor(null);
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        if(inflater != null) {
            inflater.inflate(R.menu.item_menu, menu);
        }
        return true;
    }

    public boolean onPrepareActionMode(ActionMode p1, Menu menu) {
		List<Item> selectedItems = mAdapter.getSelectedItems();
		MenuItem editItem = menu.findItem(R.id.edit_btn);
		editItem.setEnabled(selectedItems.size()==1);
		MenuItem deleteItem = menu.findItem(R.id.delete_btn);
		deleteItem.setEnabled(selectedItems.size()>=1);
        return true;
    }

    public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
		DialogFragment fragment = null;

		switch(item.getItemId()) {
			case R.id.edit_btn:
			    fragment = getEditSelectionFragment();
				actionMode.finish();
				break;
			case R.id.delete_btn:
			    deleteSelection();
				return true;
		}

		if(fragment != null) {
			fragment.show(getFragmentManager(), "dialog");
			actionMode.finish();
			return true;
		}

        return false;
    }

    public void onDestroyActionMode(ActionMode p1) {
        mActionMode = null;
    }

	private void deleteSelection() {
		List<Item> selectedItems = mAdapter.getSelectedItems();
		for(Item item: selectedItems) {
			Uri uri = UriBuilder.getItemUri(item.getId());
			try {
				ItemController.delete(mActivity, uri);
			} catch (RemoteException e) {}
		}
	}

	private DialogFragment getEditSelectionFragment() {
		DialogFragment fragment = new ItemFragment(mActivity);
		Item item = mAdapter.getSelectedItems().get(0);
		Bundle bundle = new Bundle(1);
		bundle.putParcelable(Constants.ITEM_PARAMETER, item);
		fragment.setArguments(bundle);
		return fragment;
	}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(mActionMode != null) {
            mAdapter.toggleSelection(i);
			mActionMode.invalidate();
        }
    }

    @Override
    public void shake() {
        triggerDecider();
    }
}
