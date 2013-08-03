package net.alphaDev.Decider.Controllers;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;
import android.os.RemoteException;
import net.alphaDev.Decider.Adapter.DecideListAdapter;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.Model.List;
import net.alphaDev.Decider.Util.UriBuilder;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class ListController {
    public static CursorLoader getLists(Context context) {
        final CursorLoader loader = new CursorLoader(context);
        loader.setUri(UriBuilder.getListUri());
        loader.setProjection(List.DEFAULT_PROJECTION);
        return loader;
    }

	public static void save(Context context, String label, DecideListAdapter list) throws RemoteException {
		final Uri baseUri = UriBuilder.getListUri();
		final ContentResolver resolver = context.getContentResolver();
		final ContentProviderClient client = resolver.acquireContentProviderClient(baseUri);

		final ContentValues values = new ContentValues();
		values.put(List.Columns.LABEL, label);

		long id = ItemController.findParentList(list);
		if(id == -1) {
			client.insert(baseUri, values);
		} else {
			Uri itemId = UriBuilder.getListUri(id);
			client.update(itemId, values, null, null);
		}

		for(int i = 0; i < list.getCount(); i++) {
			DecideListAdapter.InnerItem item = (DecideListAdapter.InnerItem) list.getItem(i);
			item.list = id;
			ItemController.save(context, item);
		}

		client.release();
	}
}
