package net.alphaDev.Decider.Storage;

import android.content.UriMatcher;
import android.net.Uri;
import net.alphaDev.Decider.Model.List;
import net.alphaDev.Decider.R;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderListProvider
        extends AbstractDeciderProvider {

	public static final String AUTHORITY = "net.alphaDev.Decider.Storage.DeciderListProvider";

	static {
		sURIMatcher.addURI(AUTHORITY, "list", R.id.PROVIDER_LIST);
		sURIMatcher.addURI(AUTHORITY, "list/#", R.id.PROVIDER_LIST_ID);
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
		return List.TABLE;
	}

	@Override
	public String getMime() {
		return "vnd.net.alphaDev.Decider.Model.List";
	}
}
