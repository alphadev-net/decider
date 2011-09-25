package net.alphaDev.Decider;

import android.app.Dialog;
import net.alphaDev.Decider.Actions.addAction;
import net.alphaDev.Decider.Actions.decideAction;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import net.alphaDev.Decider.Storage.deciderStorage;
import net.alphaDev.Decider.Storage.deciderStorageFactory;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class Decider extends Activity {

    // Setup DialogIDs
    public static final int DIALOG_ABOUT_ID = 0;
    public static final int DIALAG_SAVE_ID = 1;
    public static final int DIALAG_LOAD_ID = 2;

    // Fields for the UI Components
    private Button decideButton;
    private Button addButton;
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
        addButton = (Button) findViewById(R.id.addbtn);
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
                // (has it's own ActionHandling)
                saveList();
            case R.id.load_btn:
                // Show Load Dialog
                //TODO: implement
                showDialog(DIALAG_LOAD_ID);
            case R.id.quit_btn:
                // Exit
                //TODO: App might ask to save List?
                System.exit(0);
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
        Dialog mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.about_dialog);
        mDialog.setTitle(R.string.about_title);
        //TODO: implement clickable links
        return mDialog;
    }

    private Dialog createSaveDialog() {
        return new AlertDialog.Builder(this)
        .setMessage(R.string.list_title_dialog_message)
        .create();
    }

    private void prepareSaveDialog(Dialog mDialog) {
        final EditText input = new EditText(this);
//        dialog.
//        mDialog
        //dialog.setPositiveButton(R.string.save_btn, new saveListAction(this, input));
    }

    private void prepareLoadDialog(Dialog dialog) {
        
    }

    private Dialog createLoadDialog() {
        ListAdapter list = getDatabase().getLists();
        return new AlertDialog.Builder(this)
        .setMessage(R.string.load_title_dialog_message)
        //TODO: load list of items
        .setItems(null, null)
        .create();
    }

    private void saveList() {
        if(adapter.getItemsCount() > 0) {
            ITitle dataSource = (decideListAdapter) adapter;
            if(dataSource.hasTitle()) {
                showDialog(DIALAG_SAVE_ID);
            }

            deciderStorage mDB = getDatabase();
            mDB.writeList(dataSource.getTitle(), (decideListAdapter) adapter);
        }
    }
}