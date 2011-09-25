package net.alphaDev.Decider.Storage;

import android.widget.ListAdapter;
import net.alphaDev.Decider.decideListAdapter;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public interface deciderStorage {
    public void writeList(String label, decideListAdapter entries);
    public ListAdapter getLists();
    public decideListAdapter readList(int listID);
}