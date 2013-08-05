package net.alphaDev.Decider.Controllers;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.Util.UriBuilder;
import android.os.RemoteException;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class ItemController {
    public static CursorLoader getItemsByListId(Context context, long listId) {
        final String[] selection = {Long.toString(listId)};
        final CursorLoader loader = new CursorLoader(context);
        loader.setUri(UriBuilder.getItemUri());
        loader.setSelection(Item.Columns.LIST + " = ?");
        loader.setSelectionArgs(selection);
        loader.setProjection(Item.DEFAULT_PROJECTION);
        return loader;
    }

	public static void save(Context context, Item item) throws RemoteException {
		final Uri baseUri = UriBuilder.getItemUri();
		ContentProviderClient client = getClient(context, baseUri);

		final ContentValues values = new ContentValues();
		values.put(Item.Columns.LABEL, item.getLabel().toString());
		values.put(Item.Columns.LIST, item.getList());

		client.insert(baseUri, values);
		client.release();
	}

	private static ContentProviderClient getClient(Context context, Uri baseUri) {
		final ContentResolver resolver = context.getContentResolver();
		return resolver.acquireContentProviderClient(baseUri);
	}

	public static void delete(Context context, Uri uri) throws RemoteException {
		ContentProviderClient client = getClient(context, UriBuilder.getItemUri());
		client.delete(uri, null, null);
		client.release();
	}

	public static void update(Context context, Item item) throws RemoteException {
		final Uri baseUri = UriBuilder.getItemUri();
		ContentProviderClient client = getClient(context, baseUri);

		final ContentValues values = new ContentValues();
		values.put(Item.Columns.LABEL, item.getLabel().toString());
		values.put(Item.Columns.LIST, item.getList());

    	Uri itemId = UriBuilder.getItemUri(item.getId());
    	client.update(itemId, values, null, null);

    	client.release();
	}
}
