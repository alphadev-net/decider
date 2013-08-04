package net.alphaDev.Decider.Adapter;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.alphaDev.Decider.Controllers.ItemController;
import net.alphaDev.Decider.Model.Item;

import java.util.ArrayList;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends BaseAdapter {

    private long mListId = -1;
    private ArrayList<InnerItem> mEntries;
    private boolean isAltered = false;
    private Context mContext;
	private String mTitle;

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

    public void setSelected(int pos, boolean selected) {
        mEntries.get(pos).selected = selected;
        notifyDataSetChanged();
    }

    public boolean isSelected(int pos) {
        return mEntries.get(pos).selected;
    }

    public void toggleSelection(int pos) {
        boolean currentState = isSelected(pos);
        setSelected(pos, !currentState);
    }

    public void resetSelection() {
        for(InnerItem item: mEntries) {
            item.selected = false;
        }
        notifyDataSetChanged();
    }

    public CharSequence[] dumpItems() {
        CharSequence[] items = new CharSequence[mEntries.size()];
        for(int i=0; i<mEntries.size(); i++) {
            items[i] = mEntries.get(i).label;
        }
        return items;
    }

    public void swapCursor(Cursor cursor) {
        resetEntries();
        if(cursor == null) {
            return;
        }

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(Item.Columns._ID);
            int listIndex = cursor.getColumnIndex(Item.Columns.LIST);
            int labelIndex = cursor.getColumnIndex(Item.Columns.LABEL);
            do {
                InnerItem item = new InnerItem();
                item.id = cursor.getLong(idIndex);
                item.list = cursor.getLong(listIndex);
                item.label = cursor.getString(labelIndex);
                mEntries.add(item);
            } while(cursor.moveToNext());
        }

        mListId = ItemController.findParentList(this);
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
        textView.setText(mEntries.get(i).getLabel());

        return convertView;
    }

    public class InnerItem {
        public long id;
        public String label;
        public long list;
        public boolean selected;
    }

}
