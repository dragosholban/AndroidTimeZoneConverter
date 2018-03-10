package dragosholban.com.timezoneconverter;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Date localDate = new Date();
    Button dateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView userTime = findViewById(R.id.userTime);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                userTime.setText((seekBar.getProgress() < 10 ? "0" + Integer.toString(seekBar.getProgress()) : Integer.toString(seekBar.getProgress())) + ":00");
                localDate.setHours(seekBar.getProgress());
                if (fromUser) {
                    localDate.setMinutes(0);
                }
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
    }
}
