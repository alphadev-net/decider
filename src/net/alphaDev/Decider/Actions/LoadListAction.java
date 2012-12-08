package net.alphaDev.Decider.Actions;

import net.alphaDev.Decider.DeciderActivity;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class LoadListAction
		implements OnItemClickListener {
	private final DeciderActivity caller;
	private final Dialog parent;

	public LoadListAction(DeciderActivity caller, Dialog parent) {
		this.caller = caller;
		this.parent = parent;
	}

	@Override
	public void onItemClick(AdapterView<?> dialogAdapter, View selectedView, int position, long row) {
		caller.setAdapter(caller.getDatabase().readList((int) row));
		parent.dismiss();
	}
}