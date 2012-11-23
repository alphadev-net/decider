package net.alphaDev.Decider.Util;

import android.app.Activity;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import net.alphaDev.Decider.DeciderActivity;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class Utility {
    public static WheelViewAdapter extractAdapter(Activity activity) {
        return extractWheel(activity).getViewAdapter();
    }

    public static WheelView extractWheel(Activity activity) {
        return ((DeciderActivity) activity).getWheel();
    }
}