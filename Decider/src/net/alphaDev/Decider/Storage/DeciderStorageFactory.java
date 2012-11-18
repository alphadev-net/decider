package net.alphaDev.Decider.Storage;

import android.content.Context;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderStorageFactory {
    public static DeciderStorage buildStorage(Context context) {
        return new DeciderDatabaseStorage(context);
    }
}