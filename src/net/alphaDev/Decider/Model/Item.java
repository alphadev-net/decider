package net.alphaDev.Decider.Model;

import android.provider.BaseColumns;

public class Item {
	public static final String TABLE = "entries";

	public static final class Columns implements BaseColumns {
		public static final String LABEL = "label";
		public static final String ITEM = "item";
	}

	public static final String[] DEFAULT_PROJECTION = new String[]{
		Columns._ID,
		Columns.LABEL,
		Columns.ITEM
	};
}
