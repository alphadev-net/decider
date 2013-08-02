package net.alphaDev.Decider.Actions;

import android.content.DialogInterface;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DialogCancelledAction implements DialogInterface.OnClickListener {
    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(DialogInterface dialog, int id) {
        dialog.dismiss();
    }
}