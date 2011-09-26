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
import android.widget.TextView;
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
    private static final String DATABASE_NAME = "decider.db";
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
        populateDB(mDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Perform database changes after app update
        Log.w("Decider", "Upgrading database, this will drop tables and recreate.");
        db.execSQL("DROP TABLE IF EXISTS " + LIST_LABELS);
        db.execSQL("DROP TABLE IF EXISTS " + LIST_ENTRIES);
        onCreate(db);
    }

    private void populateDB(SQLiteDatabase mDB) {
        mDB.beginTransaction();
        int last = (int) writeListEntry(mDB, "Yes / No");
        writeEntriesEntry(mDB, last, "Yes");
        writeEntriesEntry(mDB, last, "No");
        last = (int) writeListEntry(mDB, "Yes / No / Maybe");
        writeEntriesEntry(mDB, last, "Yes");
        writeEntriesEntry(mDB, last, "No");
        writeEntriesEntry(mDB, last, "Maybe");
        last = (int) writeListEntry(mDB, "A / B / C / D / E");
        writeEntriesEntry(mDB, last, "A");
        writeEntriesEntry(mDB, last, "B");
        writeEntriesEntry(mDB, last, "C");
        writeEntriesEntry(mDB, last, "D");
        writeEntriesEntry(mDB, last, "E");
        mDB.setTransactionSuccessful();
        mDB.endTransaction();
    }

    public void writeList(String label, decideListAdapter entries) {
        SQLiteDatabase mDB = getWritableDatabase();
        mDB.beginTransaction();

        // write list metadata to the database
        int lastID = (int) writeListEntry(mDB, label);

        // write the list contents to the database
        for (int i = 0; i < entries.getItemsCount(); i++) {
            writeEntriesEntry(mDB, lastID, entries.getItemText(i).toString());
        }
        mDB.setTransactionSuccessful();
        mDB.endTransaction();
    }

    private long writeListEntry(SQLiteDatabase mDB, String label) {
        ContentValues labelValues = new ContentValues(1);
        labelValues.put("LABEL", label);
        return mDB.insert(LIST_LABELS, null, labelValues);
    }

    private void writeEntriesEntry(SQLiteDatabase mDB, int parent, String label) {
        ContentValues entrieValues = new ContentValues(2);
        entrieValues.put("LABEL", parent);
        entrieValues.put("ITEM", label);
        mDB.insert(LIST_ENTRIES, null, entrieValues);
    }

    public ListAdapter getLists() {
        SQLiteDatabase mDB = getReadableDatabase();
        final Cursor cursor = mDB.rawQuery("SELECT * FROM " + LIST_LABELS, null);
        return new BaseAdapter() {
            public int getCount() {
                return cursor.getCount();
            }

            public Object getItem(int i) {
                cursor.moveToPosition(i);
                return cursor.getString(cursor.getColumnIndex("LABEL"));
            }

            public long getItemId(int i) {
                cursor.moveToPosition(i);
                return cursor.getInt(cursor.getColumnIndex("ID"));
            }

            public View getView(int i, View view, ViewGroup vg) {
                if(view == null) {
                    TextView newView = new TextView(mContext);
                    newView.setTextSize(24);
                    newView.setText(getItem(i).toString());
                    return newView;
                }
                return view;
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
        String sql = "SELECT * FROM " + LIST_ENTRIES + " WHERE LABEL = ?";
        String[] selectionArgs = new String[]{Integer.toString(listID)};

        // Execute query
        Cursor listCursor = mDB.rawQuery(sql, selectionArgs);
        while(listCursor.moveToNext()) {
            // Add listItem label to tmpList
            tmpList.add(listCursor.getString(listCursor.getColumnIndex("ITEM")));
        }

        // Populate mAdapter with the temporary List and return to caller
        mAdapter.setList(tmpList);
        return mAdapter;
    }
}