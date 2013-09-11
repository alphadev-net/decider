package net.alphaDev.Decider.Adapter;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.alphaDev.Decider.Controllers.ItemController;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.Model.List;
import net.alphaDev.Decider.Util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends BaseAdapter
        implements Parcelable {

    private List mList;
    private ArrayList<Item> mEntries;
    private HashMap<Item, Boolean> mSelection;
    private Context mContext;

	public DecideListAdapter(Context context) {
        mContext = context;
        resetEntries();
	}

    public DecideListAdapter(Context context, Parcel in) {
        this(context);
        ClassLoader listLoader = List.class.getClassLoader();
        mList = (List) in.readParcelable(listLoader);

        ClassLoader itemLoader = Item.class.getClassLoader();
        Parcelable[] items = in.readParcelableArray(itemLoader);
        for(Parcelable item: items) {
            addEntry((Item)item);
        }
    }

	public void updateEntry(long id, CharSequence newItem) {
		for(Item item: mEntries) {
			if(item.getId() == id) {
				item.setLabel(newItem);
			}
		}
	}

    public void addEntry(CharSequence label) {
        final Item item = new Item(0, label);
        addEntry(item);
    }

    public void addEntry(Item item) {
        mEntries.add(item);
        mSelection.put(item, false);
        notifyDataSetChanged();
    }

    public void resetEntries() {
        mEntries = new ArrayList<Item>();
        resetSelection();
        notifyDataSetChanged();
    }

    public void setSelected(int pos, boolean selected) {
        Item item = mEntries.get(pos);
        mSelection.put(item, selected);
        notifyDataSetChanged();
    }

    public boolean isSelected(int pos) {
        Item item = mEntries.get(pos);
        return mSelection.get(item);
    }

    public void toggleSelection(int pos) {
        boolean currentState = isSelected(pos);
        setSelected(pos, !currentState);
    }

    public void resetSelection() {
        mSelection = new HashMap<Item, Boolean>();
        notifyDataSetChanged();
    }

    public Item[] dumpItems() {
        Item[] items = new Item[mEntries.size()];
        return mEntries.toArray(items);
    }

    public void swapCursor(Cursor cursor) {
        resetEntries();
        if(cursor == null) {
            return;
        }

        Bundle extras = cursor.getExtras();
        mList = extras.getParcelable(Constants.LIST_PARAMETER);

        if(cursor.moveToFirst()) {
            do {
                addEntry(new Item(cursor));
            } while(cursor.moveToNext());
        }

        notifyDataSetChanged();
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
        return mEntries.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.simple_selectable_list_item, viewGroup, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text1);
		Item item = mEntries.get(i);
		if(item == null) {
			textView.setText(item.getLabel());
		}
        return convertView;
    }

	public java.util.List<Item> getSelectedItems() {
		ArrayList<Item> selected = new ArrayList<Item>();
		for(Item item: mEntries) {
			if(mSelection.get(item)) {
				selected.add(item);
			}
		}
		return selected;
	}

    public List getList() {
        return mList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mList, i);
        Parcelable[] array = new Parcelable[mEntries.size()];
        array = mEntries.toArray(array);
        parcel.writeParcelableArray(array, i);
    }
}
