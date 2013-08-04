package net.alphaDev.Decider.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class Item
        implements Parcelable {

	public static final String TABLE = "entries";

    private static final int savedFlag = 1;
    private static final int deletedFlag = 2;

    public static final class Columns implements BaseColumns {
		public static final String LIST = "label";
		public static final String LABEL = "item";
	}

	public static final String[] DEFAULT_PROJECTION = new String[]{
		Columns._ID,
		Columns.LIST,
		Columns.LABEL
	};

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            Item item = new Item();
            item.mId = in.readLong();
            item.mList = in.readLong();
            item.mLabel = in.readString();
            int flags = in.readInt();
            item.mSaved = (flags|savedFlag) == flags;
            item.mDeleted = (flags|deletedFlag) == flags;
            return item;
        }

        @Override
        public Item[] newArray(int i) {
            return new Item[i];
        }
    };

    private long mId;
    private CharSequence mLabel;
    private long mList;
    private boolean mSaved;
    private boolean mDeleted;

    private Item()  {
    }

    public Item(long list, CharSequence label) {
        mLabel = label;
        mList = list;
        mSaved = false;
    }

    public Item(long id, long list, CharSequence label) {
        this(list, label);
        mId = id;
        mSaved = true;
    }

    public Item(Cursor c) {
        this(c.getLong(c.getColumnIndex(Item.Columns._ID)),
                c.getLong(c.getColumnIndex(Item.Columns.LIST)),
                c.getString(c.getColumnIndex(Item.Columns.LABEL)));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeLong(mList);
        parcel.writeString(mLabel.toString());
        int flags = mSaved?savedFlag:0;
        flags += mDeleted?deletedFlag:0;
        parcel.writeInt(flags);
    }

    public long getId() {
        return mId;
    }

    public CharSequence getLabel() {
        return mLabel;
    }

    public long getList() {
        return mList;
    }

    public void setSaved() {
        mSaved = true;
    }

    public boolean isSaved() {
        return mSaved;
    }
}
