package net.alphaDev.Decider.Actions;

import kankan.wheel.widget.WheelView;
import net.alphaDev.Decider.DecideListAdapter;
import net.alphaDev.Decider.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.TextView;

public class EditItemAction
		implements DialogInterface.OnClickListener {

	private final WheelView mWheel;

	public EditItemAction(WheelView mWheel) {
		this.mWheel = mWheel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(DialogInterface dialogInterface, int which) {
		final Dialog dialog = (Dialog) dialogInterface;
		final TextView text = (TextView) dialog.findViewById(R.id.DIALOG_SAVE_TEXT);

		final DecideListAdapter adapter = (DecideListAdapter) mWheel.getViewAdapter();
		final String label = text.getText().toString();

		if (label.length() > 0) {
			int pos = mWheel.getCurrentItem();
			text.setText(null);
			adapter.replaceItem(pos, label);
		}
	}
}