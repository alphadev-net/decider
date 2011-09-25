package net.alphaDev.Decider;

import android.content.Context;
import java.util.ArrayList;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 *
 * @author jan
 */
public class decideListAdapter extends AbstractWheelTextAdapter {
    private ArrayList<CharSequence> dataList;

    public decideListAdapter(Context context) {
        super(context);
        dataList = new ArrayList<CharSequence>();
    }

    public int getItemsCount() {
        return dataList.size();
    }

    @Override
    public CharSequence getItemText(int i) {
        return dataList.get(i);
    }

    public void add(CharSequence listItem) {
        dataList.add(listItem);
        notifyDataChangedEvent();
    }

    public void remove(CharSequence listItem) {
        dataList.remove(listItem);
        notifyDataChangedEvent();
    }
}