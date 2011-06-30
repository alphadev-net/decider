package net.alphaDev.Decider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 *
 * @author jan
 */
public class listItemFactory {
    public static View create(Context c, String label) {
        LayoutInflater mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View item = mInflater.inflate(R.layout.list_item, null);
        ((TextView)item.findViewById(R.id.item)).setText(label);

        return item;
    }
}