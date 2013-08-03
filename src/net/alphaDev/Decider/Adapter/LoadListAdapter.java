package net.alphaDev.Decider.Adapter;

import android.R;
import android.content.Context;
import android.widget.SimpleCursorAdapter;

import net.alphaDev.Decider.Model.List;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class LoadListAdapter
        extends SimpleCursorAdapter {

    private static final int layout = R.layout.simple_list_item_1;
    private static final int[] to = {R.id.text1};
    private static final String[] from = {List.Columns.LABEL};

	public LoadListAdapter(Context context) {
        super(context, layout, null, from, to, 0);
	}
}
