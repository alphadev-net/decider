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

    public void addEntry(CharSequence label) {
        InnerItem item = new InnerItem();
        item.label = label.toString();
        mEntries.add(item);
        notifyDataSetChanged();
        isAltered = true;
    }

    public void resetEntries() {
        mEntries = new ArrayList<InnerItem>();
        notifyDataSetChanged();
        isAltered = false;
    }


    public void swapCursor(Cursor cursor) {
        resetEntries();
        if(cursor == null) {
            return;
        }

        if(cursor.moveToFirst()) {
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
        View convertView = view;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.simple_selectable_list_item, viewGroup, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        textView.setText(mEntries.get(i).label);

        return convertView;
    }

    private class InnerItem {
        public long id;
        public String label;
        public boolean selected;
    }
}
