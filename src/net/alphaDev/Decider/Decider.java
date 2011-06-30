package net.alphaDev.Decider;

import android.app.Dialog;
import net.alphaDev.Decider.Actions.addAction;
import net.alphaDev.Decider.Actions.decideAction;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Decider extends Activity {
    public static final int DIALOG_ABOUT_ID = 0;
    public static final int DIALOG_RESULT_ID = 1;
    private ListAdapter adapter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        initComponents();
    }

    private void initComponents() {
        setContentView(R.layout.main);

        if (adapter == null) {
            adapter = new decideListAdapter(getApplicationContext());
        }

        ListView mListView = (ListView) findViewById(R.id.list);
        Button addButton = (Button) findViewById(R.id.addbtn);
        Button decideButton = (Button) findViewById(R.id.decidebtn);

        mListView.setAdapter(getListAdapter());
        addButton.setOnClickListener(new addAction(this));
        decideButton.setOnClickListener(new decideAction(this));
    }

    public ListAdapter getListAdapter() {
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.decider_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_btn:
                showDialog(DIALOG_ABOUT_ID);
                return true;
            case R.id.quit_btn:
                System.exit(0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog mDialog;

        switch (id) {
            case DIALOG_ABOUT_ID:
                mDialog = getAboutDialog();
                break;
            case DIALOG_RESULT_ID:
                mDialog = getResultDialog();
                break;
            default:
                mDialog = super.onCreateDialog(id);
        }
        return mDialog;
    }

    private Dialog getAboutDialog() {
        Dialog mDialog = new Dialog(this);

        mDialog.setContentView(R.layout.about_dialog);
        mDialog.setTitle("About...");

        TextView aboutName = (TextView) mDialog.findViewById(R.id.about_name);
        aboutName.setText(R.string.about_name);
        TextView aboutMail = (TextView) mDialog.findViewById(R.id.about_mail);
        aboutMail.setText(R.string.about_mail);
        ImageView aboutImage = (ImageView) mDialog.findViewById(R.id.about_image);
        aboutImage.setImageResource(R.drawable.icon);

        return mDialog;
    }

    private Dialog getResultDialog() {
        Dialog mDialog = new Dialog(this);

        mDialog.setContentView(R.layout.list_item);
        TextView item = (TextView) mDialog.findViewById(R.id.item);
        Button decideButton = (Button) findViewById(R.id.decidebtn);
        TextView result = (TextView) decideButton.getTag();
        decideButton.setTag(null);

        mDialog.setTitle(R.string.result_title);
        item.setText(result.getText());

        return mDialog;
    }
}