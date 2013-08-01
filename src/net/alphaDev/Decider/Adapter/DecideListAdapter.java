package net.alphaDev.Decider.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import net.alphaDev.Decider.Model.Item;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends AbstractDecideListAdapter {

	private SparseBooleanArray mSelectionTracking;
	private SparseArray<String> mEntries;
	private boolean hasCursor = false;

	public DecideListAdapter(Context context) {
		super(context, Item.Columns.LABEL);
	}

	@Override
	public Cursor swapCursor(Cursor cursor) {
		if(cursor != null) {
			hasCursor = true;
		}

		mEntries = null;
		mSelectionTracking = null;
		return super.swapCursor(cursor);
	}
}
