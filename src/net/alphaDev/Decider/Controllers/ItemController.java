package net.alphaDev.Decider.Controllers;

import android.content.Context;
import android.content.CursorLoader;

import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.Util.UriBuilder;

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
}
