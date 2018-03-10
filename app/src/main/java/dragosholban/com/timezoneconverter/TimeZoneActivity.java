package dragosholban.com.timezoneconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

public class TimeZoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zone);

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> timezones = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, timezones);
        listView.setAdapter(adapter);
    }
}
