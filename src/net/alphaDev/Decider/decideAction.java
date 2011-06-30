package net.alphaDev.Decider;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 *
 * @author jan
 */
public class decideAction implements OnClickListener {
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "test", Toast.LENGTH_LONG);
    }
}