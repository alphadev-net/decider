package net.alphaDev.Decider.Model;

import android.provider.BaseColumns;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class List {
  public static final String TABLE = "list";

  public static final class Columns implements BaseColumns {
	  public static final String LABEL = "label";
  }

  public static final String[] DEFAULT_PROJECTION = new String[]{
	  Columns._ID,
	  Columns.LABEL
  };
}
