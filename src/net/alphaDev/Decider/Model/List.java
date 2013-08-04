package net.alphaDev.Decider.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import net.alphaDev.Decider.Util.UriBuilder;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class List
        implements Parcelable {

  public static final String TABLE = "list";

  public static final class Columns implements BaseColumns {
	  public static final String LABEL = "label";
  }

  public static final String[] DEFAULT_PROJECTION = new String[]{
	  Columns._ID,
	  Columns.LABEL
  };

    public static final Parcelable.Creator<List> CREATOR = new Parcelable.Creator<List>() {
        @Override
        public List createFromParcel(Parcel in) {
            List item = new List();
            item.mId = in.readLong();
            item.mLabel = in.readString();
            item.mSaved = in.readInt()==1;
            return item;
        }

        @Override
        public List[] newArray(int i) {
            return new List[i];
        }
    };

    private long mId;
    private CharSequence mLabel;
    private boolean mSaved;

    private List() {
    }

    public List(CharSequence label) {
        mLabel = label;
        mSaved = false;
    }

    public List(long id, CharSequence label) {
        this(label);
        mId = id;
        mSaved = true;
    }

    public CharSequence getLabel() {
        return mLabel;
    }

    public boolean isSaved() {
        return mSaved;
    }

    public void setSaved(Uri newUri) {
        setSaved(UriBuilder.getId(newUri));
    }

    public void setSaved(long newId) {
        mId = newId;
        mSaved = true;
    }

    public long getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mLabel.toString());
        parcel.writeInt(mSaved?1:0);
    }
}
