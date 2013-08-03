package net.alphaDev.Decider.Controllers;

import android.content.Context;
import android.content.CursorLoader;

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
}
