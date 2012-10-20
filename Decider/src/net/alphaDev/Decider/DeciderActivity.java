package net.alphaDev.Decider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import net.alphaDev.Decider.Actions.addAction;
import net.alphaDev.Decider.Actions.decideAction;
import net.alphaDev.Decider.Actions.loadListAction;
import net.alphaDev.Decider.Actions.saveListAction;
import net.alphaDev.Decider.Storage.deciderStorage;
import net.alphaDev.Decider.Storage.deciderStorageFactory;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderActivity extends Activity {

    // Setup DialogIDs
    public static final int DIALOG_ABOUT_ID = 0;
    public static final int DIALAG_SAVE_ID = 1;
    public static final int DIALAG_LOAD_ID = 2;

    // Fields for the UI Components
    private Button decideButton;
    private ImageButton addButton;
    private WheelView wheel;

    // Datasources (flagged static for synchronized access)
    private static WheelViewAdapter adapter;
    private static deciderStorage database;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        initComponents();
    }

    private void initComponents() {
        // Load UI layout from XML
        setContentView(R.layout.main);

        // Set empty DefaultListAdapter
        if (adapter == null) {
            adapter = new decideListAdapter(getApplicationContext());
        }

        // Get references to the UI component instances
        wheel = (WheelView) findViewById(R.id.list);
        addButton = (ImageButton) findViewById(R.id.addbtn);
        decideButton = (Button) findViewById(R.id.decidebtn);

        // Set Listeners
        wheel.setViewAdapter(adapter);
        addButton.setOnClickListener(new addAction(this));
        decideButton.setOnClickListener(new decideAction(this));
    }

    // Emit WheelView to let external stuff manipulate
    public WheelView getWheel() {
        return wheel;
    }

    public void setAdapter(WheelViewAdapter adapter) {
        adapter = adapter;
        getWheel().setViewAdapter(adapter);
    }

    // Provide OptionsMenu from XML
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.decider_menu, menu);
        return true;
    }

    // Handle OptionsMenu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_btn:
                // Show About Dialog
                showDialog(DIALOG_ABOUT_ID);
                return true;
            case R.id.save_btn:
                // Show Save Dialog
                if(adapter.getItemsCount() > 0) {
                    showDialog(DIALAG_SAVE_ID);
                } else {
                    Toast.makeText(this, getString(R.string.empty_save), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.load_btn:
                // Show Load Dialog
                //TODO: implement
                showDialog(DIALAG_LOAD_ID);
                return true;
            default:
                // default Android click handling
                return super.onOptionsItemSelected(item);
        }
    }

    // Emit Database (Singleton like)
    public deciderStorage getDatabase() {
        if(database == null) {
            database = deciderStorageFactory.buildStorage(this);
        }

        return database;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog mDialog;

        switch (id) {
            case DIALOG_ABOUT_ID:
                mDialog = createAboutDialog();
                break;
            case DIALAG_SAVE_ID:
                mDialog = createSaveDialog();
                break;
            case DIALAG_LOAD_ID:
                mDialog = createLoadDialog();
                break;
            default:
                mDialog = super.onCreateDialog(id);
        }
        return mDialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DIALAG_SAVE_ID:
                // set/confirm the current lists name
                prepareSaveDialog(dialog);
                break;
            case DIALAG_LOAD_ID:
                // re-populate list of loadable lists
                prepareLoadDialog(dialog);
                break;
            default:
                super.onPrepareDialog(id, dialog);
        }
    }

    private Dialog createAboutDialog() {
        View mDialog = createDialog(R.layout.about_dialog);

        return new AlertDialog.Builder(this)
        .setView(mDialog)
        .setTitle(R.string.about_title)
        .create();
    }

    private Dialog createSaveDialog() {
        View mDialog = createDialog(R.layout.save_dialog);
        EditText input = (EditText) mDialog.findViewById(R.id.save_edittext);

        return new AlertDialog.Builder(this)
        .setView(mDialog)
        .setTitle(R.string.list_title_dialog_message)
        .setPositiveButton(R.string.save_btn, new saveListAction(this, input))
        .create();
    }

    private Dialog createLoadDialog() {
        View mDialog = createDialog(R.layout.load_dialog);

        return new AlertDialog.Builder(this)
        .setView(mDialog)
        .setTitle(R.string.load_title_dialog_message)
        .create();
    }

    private View createDialog(int dialog) {
        LayoutInflater inflater = LayoutInflater.from(this);
        return inflater.inflate(dialog, null);
    }

    private void prepareSaveDialog(Dialog mDialog) {
        EditText input = (EditText) mDialog.findViewById(R.id.save_edittext);
        ITitle currentList = (ITitle) adapter;
        input.setText(currentList.getTitle());
    }

    private void prepareLoadDialog(Dialog dialog) {
        ListAdapter listAdapter = getDatabase().getLists();
        ListView list = (ListView) dialog.findViewById(R.id.load_list);
        list.setOnItemClickListener(new loadListAction(this, dialog));
        list.setAdapter(listAdapter);
    }

    public void aboutHandler(View caller) {
        Intent intent = null;
        switch(caller.getId()) {
            case R.id.about_http:
                intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://" + getString(R.string.about_http)));
                break;
            case R.id.about_icon_link:
                intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.wpclipart.com/"));
                break;
            case R.id.about_wheel_link:
                intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://code.google.com/p/android-wheel/"));
                break;
            case R.id.about_mail:
                String ver = getString(R.string.app_name) + " " + getString(R.string.app_version);
                intent = new Intent(Intent.ACTION_SEND)
                .setType("plain/text")
                .putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.about_mail)})
                .putExtra(Intent.EXTRA_SUBJECT, ver);
                break;
            default:
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}