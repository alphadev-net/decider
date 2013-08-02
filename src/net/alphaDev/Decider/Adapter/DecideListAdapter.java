package net.alphaDev.Decider.Adapter;

import android.content.Context;
import net.alphaDev.Decider.Model.Item;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends AbstractDecideListAdapter {

	public DecideListAdapter(Context context) {
		super(context, Item.Columns.LABEL);
	}
}
