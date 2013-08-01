package net.alphaDev.Decider.Storage;

import java.util.ArrayList;
import java.util.Collection;

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
import android.provider.BaseColumns;
import net.alphaDev.Decider.Model.List;
import net.alphaDev.Decider.Model.Item;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderSQLiteOpenHelper
		extends SQLiteOpenHelper {

	// Set up constants used throughout class
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "decider.db";

	public DeciderSQLiteOpenHelper(Context context) {
		// Let the OS handle the Database stuff
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase mDB) {
		Log.w("Decider", "Creating database tables");
		// Setup tables on first DB usage;
		String sql = "CREATE TABLE " + List.TABLE + " ("
		           + List.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				   + List.Columns.LABEL + " TEXT)";
		mDB.execSQL(sql);

		sql = "CREATE TABLE " + Item.TABLE + " ("
		           + Item.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				   + Item.Columns.LABEL + " INTEGER, "
				   + Item.Columns.ITEM + " TEXT)";
		mDB.execSQL(sql);
		populateDB(mDB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion == 1 & newVersion == 2) {
			db.execSQL("ALTER TABLE ");
		}

		// Perform database changes after app update
		Log.w("Decider", "Upgrading database, this will drop tables and recreate.");
		db.execSQL("DROP TABLE IF EXISTS ?", new String[]{ List.TABLE });
		db.execSQL("DROP TABLE IF EXISTS ?", new String[]{ Item.TABLE });
		onCreate(db);
	}

	private void populateDB(SQLiteDatabase mDB) {
		mDB.beginTransaction();
		int last = (int) writeListEntry(mDB, "Yes / No");
		writeItemsEntry(mDB, last, "Yes");
		writeItemsEntry(mDB, last, "No");
		last = (int) writeListEntry(mDB, "Yes / No / Maybe");
		writeItemsEntry(mDB, last, "Yes");
		writeItemsEntry(mDB, last, "No");
		writeItemsEntry(mDB, last, "Maybe");
		last = (int) writeListEntry(mDB, "A / B / C / D / E");
		writeItemsEntry(mDB, last, "A");
		writeItemsEntry(mDB, last, "B");
		writeItemsEntry(mDB, last, "C");
		writeItemsEntry(mDB, last, "D");
		writeItemsEntry(mDB, last, "E");
		mDB.setTransactionSuccessful();
		mDB.endTransaction();
	}

	private long writeListEntry(SQLiteDatabase mDB, String label) {
		ContentValues labelValues = new ContentValues(1);
		labelValues.put(List.Columns.LABEL, label);
		return mDB.insert(List.TABLE, null, labelValues);
	}

	private void writeItemsEntry(SQLiteDatabase mDB, int parent, String label) {
		ContentValues entrieValues = new ContentValues(2);
		entrieValues.put(Item.Columns.LABEL, parent);
		entrieValues.put(Item.Columns.ITEM, label);
		mDB.insert(Item.TABLE, null, entrieValues);
	}
}
