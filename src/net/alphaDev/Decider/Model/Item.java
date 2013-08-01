package net.alphaDev.Decider.Model;

import android.provider.BaseColumns;

public class Item {
	public static final String TABLE = "entries";

	public static final class Columns implements BaseColumns {
		public static final String LIST = "label";
		public static final String LABEL = "item";
	}

	public static final String[] DEFAULT_PROJECTION = new String[]{
		Columns._ID,
		Columns.LIST,
		Columns.LABEL
	};
}
