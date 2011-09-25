package net.alphaDev.Decider.Storage;

import android.content.Context;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class deciderStorageFactory {
    public static deciderStorage buildStorage(Context context) {
        return new deciderDatabaseStorage(context);
    }
}