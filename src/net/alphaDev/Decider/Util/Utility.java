package net.alphaDev.Decider.Util;

import android.app.Activity;
import android.widget.ListAdapter;
import net.alphaDev.Decider.Decider;

/**
 *
 * @author jan
 */
public class Utility {
    public static ListAdapter extractAdapter(Activity activity) {
        return ((Decider) activity).getListAdapter();
    }
}