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
import android.widget.TextView;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;

public class Decider extends Activity {

    public static final int DIALOG_ABOUT_ID = 0;
    private Button decideButton;
    private Button addButton;
    private WheelView wheel;
    private static WheelViewAdapter adapter;

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

        wheel = (WheelView) findViewById(R.id.list);
        addButton = (Button) findViewById(R.id.addbtn);
        decideButton = (Button) findViewById(R.id.decidebtn);

        wheel.setViewAdapter(adapter);
        addButton.setOnClickListener(new addAction(this));
        decideButton.setOnClickListener(new decideAction(this));
    }

    public WheelView getWheel() {
        return wheel;
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
                mDialog = createAboutDialog();
                break;
            default:
                mDialog = super.onCreateDialog(id);
        }
        return mDialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DIALOG_ABOUT_ID:
                prepareAboutDialog(dialog);
                break;
            default:
                super.onPrepareDialog(id, dialog);
        }
    }

    private Dialog createAboutDialog() {
        Dialog mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.about_dialog);
        mDialog.setTitle(R.string.about_title);
        return mDialog;
    }

    private void prepareAboutDialog(Dialog mDialog) {
        TextView aboutName = (TextView) mDialog.findViewById(R.id.about_name);
        aboutName.setText(R.string.about_name);
        TextView aboutMail = (TextView) mDialog.findViewById(R.id.about_mail);
        aboutMail.setText(R.string.about_mail);
        ImageView aboutImage = (ImageView) mDialog.findViewById(R.id.about_image);
        aboutImage.setImageResource(R.drawable.icon);
    }
}