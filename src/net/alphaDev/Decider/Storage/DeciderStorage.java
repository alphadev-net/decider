package net.alphaDev.Decider.Storage;

import android.widget.ListAdapter;
import net.alphaDev.Decider.DecideListAdapter;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public interface DeciderStorage {
    public void writeList(String label, DecideListAdapter entries);
    public ListAdapter getLists();
    public DecideListAdapter readList(int listID);
}