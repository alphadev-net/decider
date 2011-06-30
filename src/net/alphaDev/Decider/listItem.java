package net.alphaDev.Decider;

import android.content.Context;
import android.widget.TextView;

/**
 *
 * @author jan
 */
public class listItem extends TextView{
    public listItem(Context context, String label) {
        super(context);
        setText(label);
    }

    @Override
    public String toString() {
        return getText().toString();
    }
}