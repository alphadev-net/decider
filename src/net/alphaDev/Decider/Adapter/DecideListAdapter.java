package net.alphaDev.Decider.Adapter;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.alphaDev.Decider.Model.Item;

import java.util.ArrayList;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends BaseAdapter {

    private ArrayList<InnerItem> mEntries;
    private boolean isAltered = false;
    private Context mContext;

	public DecideListAdapter(Context context) {
        mContext = context;
        resetEntries();
	}

    private void addEntry(String label) {
        InnerItem item = new InnerItem();
        item.label = label;
        mEntries.add(item);
        notifyDataSetChanged();
        isAltered = true;
    }

    private void resetEntries() {
        mEntries = new ArrayList<InnerItem>();
        notifyDataSetChanged();
        isAltered = false;
    }


    public void swapCursor(Cursor cursor) {
        if(cursor == null) {
            return;
        }

        if(cursor.moveToFirst()) {
            resetEntries();
            int idIndex = cursor.getColumnIndex(Item.Columns._ID);
            int labelIndex = cursor.getColumnIndex(Item.Columns.LABEL);
            do {
                InnerItem item = new InnerItem();
                item.id = cursor.getLong(idIndex);
                item.label = cursor.getString(labelIndex);
                mEntries.add(item);
            } while(cursor.moveToNext());
        }

        notifyDataSetChanged();
        isAltered = false;
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int i) {
        return mEntries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mEntries.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.simple_selectable_list_item, viewGroup);
        }

        TextView textView = (TextView) view.findViewById(R.id.text1);
        textView.setText(mEntries.get(i).label);

        return view;
    }

    private class InnerItem {
        public long id;
        public String label;
        public boolean selected;
    }
}
