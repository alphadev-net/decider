package net.alphaDev.Decider.Storage;

import android.content.UriMatcher;
import android.net.Uri;
import net.alphaDev.Decider.Model.Item;
import net.alphaDev.Decider.R;

public class DeciderItemProvider
        extends AbstractDeciderProvider {

	public static final String AUTHORITY = "net.alphaDev.Decider.DeciderItemProvider";

	static {
		sURIMatcher.addURI(AUTHORITY, "item", R.id.PROVIDER_ITEM);
		sURIMatcher.addURI(AUTHORITY, "item/#", R.id.PROVIDER_ITEM_ID);
	}

	public static boolean isResponsible(Uri uri) {
		return sURIMatcher.match(uri) != UriMatcher.NO_MATCH;
	}

	@Override
	public String getAuthority() {
		return AUTHORITY;
	}

	@Override
	protected String getTableName() {
		return Item.TABLE;
	}

	@Override
	public String getMime() {
		return "vnd.net.alphaDev.Decider.Model.Item";
	}
}
