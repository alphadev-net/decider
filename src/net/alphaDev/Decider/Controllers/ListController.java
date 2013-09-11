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

	public static void save(Context context, List list) throws RemoteException {
		final Uri baseUri = UriBuilder.getListUri();
		final ContentProviderClient client = getClient(context, baseUri);

		final ContentValues values = new ContentValues();
		values.put(List.Columns.LABEL, list.getLabel().toString());

		if(list.isSaved()) {
            Uri itemId = UriBuilder.getListUri(list.getId());
            client.update(itemId, values, null, null);
		} else {
            Uri uri = client.insert(baseUri, values);
            list.setSaved(uri);
		}

		client.release();
	}

	private static ContentProviderClient getClient(Context context, Uri baseUri) {
		final ContentResolver resolver = context.getContentResolver();
		return resolver.acquireContentProviderClient(baseUri);
	}
}
