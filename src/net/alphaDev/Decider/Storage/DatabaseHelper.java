package net.alphaDev.Decider.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.Model.List;
import net.alphaDev.Decider.R;

/**
 * 
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DatabaseHelper
		extends SQLiteOpenHelper {

	// Set up constants used throughout class
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "decider.db";

	private Context mContext;

	public DatabaseHelper(Context context) {
		// Let the OS handle the Database stuff
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase mDB) {
		Log.w("Decider", "Creating database tables");
		// Setup tables on first DB usage;
		createV2List(mDB);
		createV2Item(mDB);
		populateDB(mDB);
	}

	private void createV2Item(SQLiteDatabase mDB) throws SQLException {
		String sql = "CREATE TABLE " + Item.TABLE + " ("
			+ Item.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Item.Columns.LIST + " INTEGER, "
			+ Item.Columns.LABEL + " TEXT)";
		mDB.execSQL(sql);
	}

	private void createV2List(SQLiteDatabase mDB) throws SQLException {
		String sql = "CREATE TABLE " + List.TABLE + " ("
			+ List.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ List.Columns.LABEL + " TEXT)";
		mDB.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion == 1 & newVersion == 2) {
		//	db.execSQL("ALTER TABLE ");
		}

		// Perform database changes after app update
		Log.w("Decider", "Upgrading database, this will drop tables and recreate.");
		db.execSQL("DROP TABLE IF EXISTS ?", new String[]{ List.TABLE });
		db.execSQL("DROP TABLE IF EXISTS ?", new String[]{ Item.TABLE });
		onCreate(db);
	}

	private void populateDB(SQLiteDatabase mDB) {
		mDB.beginTransaction();
		writeArray(mDB, R.array.yn);
		writeArray(mDB, R.array.ynm);
		writeArray(mDB, R.array.abcde);
		mDB.setTransactionSuccessful();
		mDB.endTransaction();
	}

	private void writeArray(SQLiteDatabase mDB, int array) {
		StringBuilder sb = new StringBuilder();
		String[] items = mContext.getResources().getStringArray(array);
		for(String item: items) {
			sb.append(" / ");
			sb.append(item);
		}
		String label = sb.substring(3);
		long last = writeListEntry(mDB, label);
		for(String item: items) {
			writeItemsEntry(mDB, last, item);
		}
	}

	private long writeListEntry(SQLiteDatabase mDB, String label) {
		ContentValues labelValues = new ContentValues(1);
		labelValues.put(List.Columns.LABEL, label);
		return mDB.insert(List.TABLE, null, labelValues);
	}

	private void writeItemsEntry(SQLiteDatabase mDB, long parent, String label) {
		ContentValues entrieValues = new ContentValues(2);
		entrieValues.put(Item.Columns.LIST, parent);
		entrieValues.put(Item.Columns.LABEL, label);
		mDB.insert(Item.TABLE, null, entrieValues);
	}
}
