package dragosholban.com.timezoneconverter;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

public class SelectTimezonesActivity extends AppCompatActivity {
    ArrayList<String> selectedTimezones = new ArrayList<>();
    TimeZoneAdapter adapter;
    ArrayList<String> timezones;
    ListView listView;
    boolean showAll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_timezones);

        setTitle("Choose Time Zones");

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("selectedTimezonesBundle");
        selectedTimezones = bundle.getStringArrayList("selectedTimezones");
        timezones = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs()));

        listView = findViewById(R.id.listView);
        adapter = new TimeZoneAdapter(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, timezones);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listView.isItemChecked(i)) {
                    selectedTimezones.add(adapter.getItem(i));
                } else {
                    selectedTimezones.remove(adapter.getItem(i));
                }
            }
        });

        checkSelectedTimezones();
    }

    public void done(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("selectedTimezones", selectedTimezones);
        Intent result = new Intent(this, MainActivity.class);
        result.putExtra("selectedTimezonesBundle", bundle);
        setResult(RESULT_OK, result);
        finish();
    }

    public void showChecked(View view) {
        Button button = (Button) view;
        adapter.clear();
        if (showAll) {
            for (String timezone : selectedTimezones) {
                adapter.add(timezone);
            }
            adapter.notifyDataSetChanged();

            button.setText("Show All");
            showAll = false;
        } else {
            for (String timezone : TimeZone.getAvailableIDs()) {
                adapter.add(timezone);
            }
            adapter.notifyDataSetChanged();

            button.setText("Show Checked");
            showAll = true;
        }

        checkSelectedTimezones();
    }

    public void uncheckAll(View view) {
        selectedTimezones.clear();
        checkSelectedTimezones();
    }

    private void checkSelectedTimezones() {
        for(int j = 0; j < adapter.getCount(); j++) {
            if (selectedTimezones.contains(adapter.getItem(j))) {
                listView.setItemChecked(j, true);
            } else {
                listView.setItemChecked(j, false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int i) {
                        checkSelectedTimezones();
                    }
                });

                return true;
            }
        });

        return true;
    }
}
