package net.alphaDev.Decider;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import net.alphaDev.Decider.Actions.deleteAction;

/**
 *
 * @author jan
 */
public class listItemFactory {
    public static View create(Context c, String label) {
        LayoutInflater mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View item = mInflater.inflate(R.layout.list_item, null);
        ((TextView)item.findViewById(R.id.item)).setText(label);
        ((TextView)item.findViewById(R.id.removebtn)).setOnClickListener(new deleteAction(c));

        return item;
    }
}