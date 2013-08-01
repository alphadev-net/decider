package net.alphaDev.Decider.Adapter;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.widget.SimpleCursorAdapter;
import android.R;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends SimpleCursorAdapter {

	private static final int layout = R.layout.simple_list_item_1;
	private static final int[] to = new int[]{ R.id.text1};

	public DecideListAdapter(Context context, String... from) {
		super(context, layout, null, from, to);
	}
}
