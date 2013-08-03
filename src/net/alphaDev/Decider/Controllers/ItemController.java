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

    public static long findParentList(DecideListAdapter adapter) {
        long result = -1;
        for(int i=0; i<adapter.getCount(); i++) {
            DecideListAdapter.InnerItem item = (DecideListAdapter.InnerItem) adapter.getItem(i);
            if(item.id != result) {
                result = item.list;
            }
        }
        return result;
    }

	public static void save(Context context, DecideListAdapter.InnerItem item) throws RemoteException {
		final Uri baseUri = UriBuilder.getItemUri();
		final ContentResolver resolver = context.getContentResolver();
		final ContentProviderClient client = resolver.acquireContentProviderClient(baseUri);

		final ContentValues values = new ContentValues();
		values.put(Item.Columns.LABEL, item.label);
		values.put(Item.Columns.LIST, item.list);

		if(item.id == -1) {
			client.insert(baseUri, values);
		} else {
			Uri itemId = UriBuilder.getItemUri(item.id);
			client.update(itemId, values, null, null);
		}

		client.release();
	}
}
