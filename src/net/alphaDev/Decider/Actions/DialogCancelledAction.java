package net.alphaDev.Decider.Actions;

import android.content.DialogInterface;

public class DialogCancelledAction implements DialogInterface.OnClickListener {
    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(DialogInterface dialog, int id) {
        dialog.dismiss();
    }
}