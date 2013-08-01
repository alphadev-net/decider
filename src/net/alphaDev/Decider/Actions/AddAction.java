package net.alphaDev.Decider.Actions;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.TextView;
import net.alphaDev.Decider.R;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class AddAction
		implements Dialog.OnClickListener {
	private final Activity caller;

	public AddAction(Activity caller) {
		this.caller = caller;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(DialogInterface dialogInterface, int which) {
		final Dialog dialog = (Dialog) dialogInterface;
		final TextView text = (TextView) dialog.findViewById(R.id.DIALOG_ADD_TEXT);

		final String label = text.getText().toString();

		if (label.length() > 0) {
			text.setText(null);

		}
	}
}
