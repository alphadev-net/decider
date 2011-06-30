package net.alphaDev.Decider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 *
 * @author jan
 */
public class decideListAdapter extends BaseAdapter {
    private ArrayList<listItem> dataList;
    private Context context;

    public decideListAdapter(Context context) {
        super();
        this.context = context;
        dataList = new ArrayList<listItem>();
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
        if(view == null) {
            TextView temp = new TextView(context);
            temp.setText(dataList.get(i).toString());
            view = temp;
        }
        return view;
    }

    void add(listItem listItem) {
        dataList.add(listItem);
        notifyDataSetChanged();
    }
}