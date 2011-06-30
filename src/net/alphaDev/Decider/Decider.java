package net.alphaDev.Decider;

import net.alphaDev.Decider.Actions.addAction;
import net.alphaDev.Decider.Actions.decideAction;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Decider extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        initComponents();
    }

    private void initComponents() {
        setContentView(R.layout.main);

        if(adapter == null) {
            adapter = new decideListAdapter(getApplicationContext());
        }

        ((ListView) findViewById(R.id.list)).setAdapter(getListAdapter());
        ((Button) findViewById(R.id.addbtn)).setOnClickListener(new addAction(this));
        ((Button) findViewById(R.id.decidebtn)).setOnClickListener(new decideAction());
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
                showAbout();
                return true;
            case R.id.quit_btn:
                System.exit(0);
            default:
                return super.onOptionsItemSelected(item);
            }
    }

    private ListAdapter adapter;

    private void showAbout() {
        
    }
}