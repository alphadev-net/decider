package net.alphaDev.Decider;

import java.util.ArrayList;
import java.util.Collection;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.content.Context;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DecideListAdapter
		extends AbstractWheelTextAdapter
		implements ITitle {
	private final ArrayList<CharSequence> dataList;
	private String listLabel;

	public DecideListAdapter(Context context) {
		super(context);
		dataList = new ArrayList<CharSequence>();
	}

	@Override
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

	public void setList(Collection<CharSequence> newList) {
		dataList.clear();
		dataList.addAll(newList);
		notifyDataChangedEvent();
	}

	public Collection<CharSequence> getList() {
		return dataList;
	}

	public void replaceItem(int pos, CharSequence newItem) {
		dataList.remove(pos);
		dataList.add(pos, newItem);
		notifyDataChangedEvent();
	}

	@Override
	public boolean hasTitle() {
		return !(listLabel != null && !listLabel.equals(""));
	}

	@Override
	public String getTitle() {
		return listLabel;
	}

	@Override
	public void setTitle(String title) {
		listLabel = title;
	}
}
