package net.alphaDev.Decider.Adapter;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.widget.SimpleCursorAdapter;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends SimpleCursorAdapter {

	private static final int layout = android.R.layout.simple_list_item_1;
	private static final String[] from = new String[]{};
	private static final int[] to = new int[]{};

	public DecideListAdapter(Context context) {
		super(context, layout, null, from, to);
	}
}
