package net.alphaDev.Decider.Util;

import android.net.Uri;
import net.alphaDev.Decider.Storage.DeciderListProvider;
import android.content.ContentUris;
import net.alphaDev.Decider.Storage.DeciderItemProvider;

public class UriBuilder {
	private static Uri getBaseUri(String type, String authority) {
		Uri.Builder builder = new Uri.Builder();
		builder.scheme("content");
		builder.authority(authority);
		builder.appendPath(type);
		return builder.build();
	}

	public static Uri getListUri() {
		return getBaseUri("list", DeciderListProvider.AUTHORITY);
	}

	public static Uri getListUri(long id) {
		return ContentUris.withAppendedId(getListUri(), id);
	}

	public static Uri getItemUri() {
		return getBaseUri("item", DeciderItemProvider.AUTHORITY);
	}

	public static Uri getItemUri(long id) {
		return ContentUris.withAppendedId(getItemUri(), id);
	}
}
