package net.alphaDev.Decider.Util;

import android.app.Activity;
import net.alphaDev.Decider.Decider;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import kankan.wheel.widget.WheelView;

/**
 *
 * @author jan
 */
public class Utility {
    public static WheelViewAdapter extractAdapter(Activity activity) {
        return extractWheel(activity).getViewAdapter();
    }

    public static WheelView extractWheel(Activity activity) {
        return ((Decider) activity).getWheel();
    }
}