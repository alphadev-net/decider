package net.alphaDev.Decider.Storage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;
import java.util.Locale;
import net.alphaDev.Decider.R;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public abstract class AbstractDeciderProvider
      extends ContentProvider {

	protected static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);	
	private DeciderSQLiteOpenHelper database;

	public abstract String getAuthority();
	protected abstract String getTableName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cursor query(Uri uri, String[] columns, String where, String[] whereArgs, String order) {
		final SQLiteDatabase db = database.getReadableDatabase();
		final SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String table = getTableName();
		Log.d(getAuthority(), String.format("using table: %s", table));
		qb.setTables(table);

		if (isSingle(uri)) {
			long id = Long.parseLong(uri.getPathSegments().get(1));
			Log.d(getAuthority(), String.format("applying constraint: %d", id));
			qb.appendWhere(BaseColumns._ID + "=" + id);
			Log.d(getAuthority(), String.format("Querying %s for id %d", getTableName(), id));
		}

		if(whereArgs != null && whereArgs.length > 0) {
			Log.d(getAuthority(), String.format("Querying %s for ? = %s", getTableName(), whereArgs[0]));
		}

		Log.d(getAuthority(), qb.buildQuery(columns, where, null, null, order, null));
		Cursor cursor = qb.query(db, columns, where, whereArgs, "", "", order);

		// Notify observers
		if (cursor != null) {
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
		}

		return cursor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = database.getWritableDatabase();
		Log.d(getAuthority(), String.format("Inserting %d values into %s", values.size(), getTableName()));

		final long _id = db.insert(getTableName(), null, values);
		if (_id == -1) {
			// SQLiteDatabase returns -1 on error
			return null;
		} else {
			final Uri contentUri = ContentUris.withAppendedId(uri, _id);
			Log.d(getAuthority(), contentUri.toString());

			// Notify observers
			getContext().getContentResolver().notifyChange(uri, null);
			return contentUri;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		int count = 0;
		final String id = uri.getPathSegments().get(1);

		if (!id.isEmpty()) {
			final SQLiteDatabase db = database.getWritableDatabase();

			where = BaseColumns._ID + "=?";
			whereArgs = new String[] { id };
			count = db.update(getTableName(), values, where, whereArgs);

			// Notify observers
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		final String id = uri.getPathSegments().get(1);
		int deleteCount = 0;

		if (!id.isEmpty()) {
			final SQLiteDatabase db = database.getWritableDatabase();
			final Locale loc = Locale.US;
			final String queryString = String.format(loc, "DELETE FROM %s WHERE %s = %s", getTableName(),
													 BaseColumns._ID, id);
			Log.d(getAuthority(), queryString);

			where = String.format(loc, "%s.%s = ?", getTableName(), BaseColumns._ID);
			whereArgs = new String[] { id };
			deleteCount = db.delete(getTableName(), where, whereArgs);

			// Notify observers
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return deleteCount;
	}
   @Override
   public boolean onCreate() {
	   database = new DeciderSQLiteOpenHelper(getContext());
	   return true;
   }

	/**
	 * Returns a vendor specific type prefix for the given URI.
	 * 
	 * @param uri
	 *            URI to be checked
	 * @return vendor specific type prefix
	 */
	@Override
	public String getType(Uri uri) {
		String base;
		if (isSingle(uri)) {
			base = "vnd.android.cursor.dir/";
		} else {
			base = "vnd.android.cursor.item/";
		}

		return String.format("%s/%s", base, getMime());
	}

	/**
	 * Returns the MIME type of the object(s) represented by a given URI.
     *
	 * @return MIME-Type as String
	 */
	protected abstract String getMime();

	/**
	 * Returns true if the Object the given URI represents is not a list of
	 * objects but a single object.
	 * 
	 * @param uri
	 *            URI of object to check
	 * @return true for single, false for lists
	 */
	protected boolean isSingle(Uri uri) {
		switch (sURIMatcher.match(uri)) {
			case R.id.PROVIDER_LIST_ID:
			case R.id.PROVIDER_ITEM_ID:
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown() {
		// Close the SQLiteOpenHelper when we get the signal to shut down.
		database.close();
		super.shutdown();
	}
}
