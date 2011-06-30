package net.alphaDev.Decider;

import android.app.Activity;
import android.os.Bundle;
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
        
        adapter = new decideListAdapter(getApplicationContext());
        ((ListView) findViewById(R.id.list)).setAdapter(getListAdapter());
        ((Button) findViewById(R.id.addbtn)).setOnClickListener(new addAction(this));
        ((Button) findViewById(R.id.decidebtn)).setOnClickListener(new decideAction());
    }

    public ListAdapter getListAdapter() {
        return adapter;
    }

    private ListAdapter adapter;
}