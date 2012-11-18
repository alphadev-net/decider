package net.alphaDev.Decider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import net.alphaDev.Decider.Actions.AddAction;
import net.alphaDev.Decider.Actions.DialogCancelledAction;
import net.alphaDev.Decider.Actions.LoadListAction;
import net.alphaDev.Decider.Actions.SaveListAction;
import net.alphaDev.Decider.Storage.DeciderStorage;
import net.alphaDev.Decider.Storage.DeciderStorageFactory;

/**
 *
 * @author Jan Seeger <jan@alphadev.net>
 */
public class DeciderActivity extends SherlockActivity {

    // Setup DialogIDs
    public static final int DIALOG_ABOUT_ID = 0;
    public static final int DIALOG_SAVE_ID = 1;
    public static final int DIALOG_LOAD_ID = 2;
    public static final int DIALOG_ADD_ID = 4;

    // Fields for the UI Components
    private WheelView wheel;

    // Datasources (flagged static for synchronized access)
    private static WheelViewAdapter adapter;
    private static DeciderStorage database;

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
            adapter = new DecideListAdapter(this);
        }

        // Get references to the UI component instances
        wheel = (WheelView) findViewById(R.id.list);

        // Set Listeners
        wheel.setViewAdapter(adapter);
    }

    // Emit WheelView to let external stuff manipulate
    public WheelView getWheel() {
        return wheel;
    }

    public void setAdapter(WheelViewAdapter adapter) {
        this.adapter = adapter;
        getWheel().setViewAdapter(adapter);
    }

    // Provide OptionsMenu from XML
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.decider_menu, menu);
        return true;
    }

    // Handle OptionsMenu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.decide_btn:
                int random = pickNumberLowerThan(adapter.getItemsCount());
                Log.i("Decider", "item: " + random);
                wheel.setCurrentItem(random, true);
                return true;
            case R.id.add_btn:
                showDialog(DIALOG_ADD_ID);
                return true;
            case R.id.about_btn:
                // Show About Dialog
                showDialog(DIALOG_ABOUT_ID);
                return true;
            case R.id.save_btn:
                // Show Save Dialog
                if(adapter.getItemsCount() > 0) {
                    showDialog(DIALOG_SAVE_ID);
                } else {
                    Toast.makeText(this, getString(R.string.empty_save), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.load_btn:
                // Show Load Dialog
                showDialog(DIALOG_LOAD_ID);
                return true;
            default:
                // default Android click handling
                return super.onOptionsItemSelected(item);
        }
    }

    private int pickNumberLowerThan(int thisNumber) {
        return (int)(Math.floor(Math.random() * thisNumber));
    }

    // Emit Database (Singleton like)
    public DeciderStorage getDatabase() {
        if(database == null) {
            database = DeciderStorageFactory.buildStorage(this);
        }

        return database;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog mDialog;

        switch (id) {
            case DIALOG_ADD_ID:
                mDialog = createAddDialog();
                break;
            case DIALOG_ABOUT_ID:
                mDialog = createAboutDialog();
                break;
            case DIALOG_SAVE_ID:
                mDialog = createSaveDialog();
                break;
            case DIALOG_LOAD_ID:
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
            case DIALOG_ADD_ID:
                prepareAddDialog(dialog);
                break;
            case DIALOG_SAVE_ID:
                // set/confirm the current lists name
                prepareSaveDialog(dialog);
                break;
            case DIALOG_LOAD_ID:
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
        input.setId(R.id.DIALOG_SAVE_TEXT);

        return new AlertDialog.Builder(this)
        .setView(mDialog)
        .setTitle(R.string.list_title_dialog_message)
        .setPositiveButton(R.string.save_btn, new SaveListAction(this, input))
        .setNeutralButton(R.string.cancel, new DialogCancelledAction())
        .create();
    }

    private Dialog createAddDialog() {
        View mDialog = createDialog(R.layout.save_dialog);
        EditText input = (EditText) mDialog.findViewById(R.id.save_edittext);
        input.setId(R.id.DIALOG_ADD_TEXT);

        return new AlertDialog.Builder(this)
        .setView(mDialog)
        .setTitle(R.string.add_title_dialog_message)
        .setPositiveButton(R.string.add_btn, new AddAction(this))
        .setNeutralButton(R.string.cancel, new DialogCancelledAction())
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
        EditText input = (EditText) mDialog.findViewById(R.id.DIALOG_SAVE_TEXT);
        ITitle currentList = (ITitle) adapter;
        input.setText(currentList.getTitle());
    }

    private void prepareAddDialog(Dialog mDialog) {
        EditText input = (EditText) mDialog.findViewById(R.id.DIALOG_ADD_TEXT);
    }

    private void prepareLoadDialog(Dialog dialog) {
        ListAdapter listAdapter = getDatabase().getLists();
        ListView list = (ListView) dialog.findViewById(R.id.load_list);
        list.setOnItemClickListener(new LoadListAction(this, dialog));
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
            case R.id.about_sherlock_link:
                intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://actionbarsherlock.com/"));
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