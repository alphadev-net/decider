package net.alphaDev.Decider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/**
 *
 * @author jan
 */
public class decideListAdapter extends BaseAdapter {
    private ArrayList<View> dataList;
    private Context context;

    public decideListAdapter(Context context) {
        super();
        this.context = context;
        dataList = new ArrayList<View>();
    }

    public int getCount() {
        return dataList.size();
    }

    public Object getItem(int i) {
        return dataList.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup vg) {
        return (View) getItem(i);
    }

    public void add(View listItem) {
        dataList.add(listItem);
        notifyDataSetChanged();
    }

    public void remove(View listItem) {
        dataList.remove(listItem);
        notifyDataSetChanged();
    }
}