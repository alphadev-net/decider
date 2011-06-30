/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.alphaDev.Decider;

import android.database.DataSetObserver;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author jan
 */
public abstract class abstractListAdapter implements ListAdapter {
    Collection<DataSetObserver> listeners;

    public abstractListAdapter() {
        listeners = new ArrayList<DataSetObserver>();
    }

    public void registerDataSetObserver(DataSetObserver dso) {
        listeners.add(dso);
    }

    public void unregisterDataSetObserver(DataSetObserver dso) {
        listeners.remove(dso);
    }
}