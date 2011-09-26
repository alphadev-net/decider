package net.alphaDev.Decider.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.Collection;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class deciderDatabaseStorage extends SQLiteOpenHelper implements deciderStorage {
    // Set up constants used throughout class
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "decider";
    private static final String LIST_LABELS = "list";
    private static final String LIST_ENTRIES = "entries";
    private Context mContext;

    public deciderDatabaseStorage(Context context) {
        // Let the OS handle the Database stuff
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase mDB) {
        Log.w("Decider", "Creating database tables");
        // Setup tables on first DB usage;
        String sql = "CREATE TABLE " + LIST_LABELS +
            " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LABEL TEXT)";
        mDB.execSQL(sql);

        sql = "CREATE TABLE " + LIST_ENTRIES +
            " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LABEL INTEGER, ITEM TEXT)";
        mDB.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Perform database changes after app update
        Log.w("Decider", "Upgrading database, this will drop tables and recreate.");
        db.execSQL("DROP TABLE IF EXISTS " + LIST_LABELS);
        db.execSQL("DROP TABLE IF EXISTS " + LIST_ENTRIES);
        onCreate(db);
    }

    public void writeList(String label, decideListAdapter entries) {
        SQLiteDatabase mDB = getWritableDatabase();
        mDB.beginTransaction();

        // write list metadata to the database
        ContentValues labelValues = new ContentValues(1);
        labelValues.put("LABEL", label);
        int lastID = (int) mDB.insert(LIST_LABELS, null, labelValues);

        // write the list contents to the database
        for (int i = 0; i < entries.getItemsCount(); i++) {
            ContentValues entrieValues = new ContentValues(2);
            entrieValues.put("LABEL", lastID);
            entrieValues.put("ITEM", entries.getItemText(i).toString());
            mDB.insert(LIST_ENTRIES, null, entrieValues);
        }
        mDB.setTransactionSuccessful();
        mDB.endTransaction();
    }

    public ListAdapter getLists() {
        SQLiteDatabase mDB = getReadableDatabase();
        final Cursor bla = mDB.rawQuery("SELECT * FROM bla", null);
        return new BaseAdapter() {
            public int getCount() {
                return bla.getCount();
            }

            public Object getItem(int i) {
                bla.moveToPosition(i);
                return bla.getColumnIndex("LABEL");
            }

            public long getItemId(int i) {
                return i;
            }

            public View getView(int i, View view, ViewGroup vg) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public decideListAdapter readList(int listID) {
        // Create new (and emtpy) decideListAdapter
        decideListAdapter mAdapter = new decideListAdapter(mContext);
        Collection<String> tmpList = new ArrayList<String>();

        // Establish read-only connection to the database
        SQLiteDatabase mDB = getReadableDatabase();

        // Prepare query for ListItems
        String sql = "SELECT * FROM " + LIST_ENTRIES + " WHERE ID='?'";
        String[] selectionArgs = new String[]{Integer.toString(listID)};

        // Execute query
        Cursor listCursor = mDB.rawQuery(sql, selectionArgs);
        while(listCursor.moveToNext()) {
            // Add listItem label to tmpList
            tmpList.add(listCursor.getString(3));
        }

        // Populate mAdapter with the temporary List and return to caller
        mAdapter.setList(tmpList);
        return mAdapter;
    }
}