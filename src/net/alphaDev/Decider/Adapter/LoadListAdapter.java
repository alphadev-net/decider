package net.alphaDev.Decider.Adapter;

import android.content.Context;
import net.alphaDev.Decider.Model.List;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class LoadListAdapter
        extends AbstractDecideListAdapter {

	public LoadListAdapter(Context context) {
		super(context, List.Columns.LABEL);
	}
}
