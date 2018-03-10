package dragosholban.com.timezoneconverter;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Date localDate = new Date();
    Button dateBtn;
    Button selectTimeZoneBtn;
    TimeZone userTimeZone;
    String[] selectedTimezones = new String[] {"Europe/Bucharest", "Europe/London", "Europe/Paris"};
    TimeZone selectedTimeZone;
    TextView convertedTimeTv;
    TextView convertedDateTv;

    private static int CHOOSE_TIME_ZONE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView userTime = findViewById(R.id.userTime);
        convertedTimeTv = findViewById(R.id.convertedTime);
        convertedDateTv = findViewById(R.id.convertedDate);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                userTime.setText((seekBar.getProgress() < 10 ? "0" + Integer.toString(seekBar.getProgress()) : Integer.toString(seekBar.getProgress())) + ":00");
                localDate.setHours(seekBar.getProgress());
                if (fromUser) {
                    localDate.setMinutes(0);
                }
                convertDate(userTimeZone, selectedTimeZone);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(localDate.getHours());

        dateBtn = findViewById(R.id.dateButton);
        dateBtn.setText(DateFormat.getDateInstance().format(localDate));

        selectTimeZoneBtn = findViewById(R.id.timeZoneButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, selectedTimezones);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTimeZone = TimeZone.getTimeZone(selectedTimezones[i]);
                convertDate(userTimeZone, selectedTimeZone);
            }
        });
    }

    public void showDatePicker(View view) {
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getFragmentManager(), "datePicker");
    }

    public void setLocalDate(Date date) {
        // we need to keep the time on the date unchanged
        int hours = localDate.getHours();
        int minutes = localDate.getMinutes();
        localDate = date;
        localDate.setHours(hours);
        localDate.setMinutes(minutes);
        Button dateBtn = findViewById(R.id.dateButton);
        dateBtn.setText(DateFormat.getDateInstance().format(localDate));
        convertDate(userTimeZone, selectedTimeZone);
    }

    public void chooseTimezone(View view) {
        Intent intent = new Intent(this, TimeZoneActivity.class);
        startActivityForResult(intent, CHOOSE_TIME_ZONE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHOOSE_TIME_ZONE_REQUEST_CODE && resultCode == RESULT_OK) {
            String timezone = data.getStringExtra("timezone");
            selectTimeZoneBtn.setText(timezone);
            userTimeZone = TimeZone.getTimeZone(timezone);
        }
        convertDate(userTimeZone, selectedTimeZone);
    }

    private void convertDate(TimeZone fromTimeZone, TimeZone toTimeZone) {
        if (fromTimeZone != null && toTimeZone != null) {
            long fromOffset = fromTimeZone.getOffset(localDate.getTime());
            long toOffset = toTimeZone.getOffset(localDate.getTime());
            long convertedTime = localDate.getTime() - (fromOffset - toOffset);
            Date convertedDate = new Date(convertedTime);
            int hours = convertedDate.getHours();
            int minutes = convertedDate.getMinutes();
            String time = (hours < 10 ? "0" + Integer.toString(hours) : Integer.toString(hours))
                    + ":" + (minutes < 10 ? "0" + Integer.toString(minutes) : Integer.toString(minutes));
            convertedTimeTv.setText(time);
            convertedDateTv.setText(DateFormat.getDateInstance().format(convertedDate));
        }
    }
}
