package net.alphaDev.Decider;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collection;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class decideListAdapter extends AbstractWheelTextAdapter implements ITitle {
    private ArrayList<CharSequence> dataList;
    private String listLabel;

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

    public void setList(Collection newList) {
        dataList.clear();
        dataList.addAll(newList);
        notifyDataChangedEvent();
    }

    public Collection getList() {
        return dataList;
    }

    public boolean hasTitle() {
        return !listLabel.isEmpty();
    }

    public String getTitle() {
        return listLabel;
    }

    public void setTitle(String title) {
        listLabel = title;
    }
}