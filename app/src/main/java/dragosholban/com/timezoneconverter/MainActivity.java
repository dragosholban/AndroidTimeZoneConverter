package dragosholban.com.timezoneconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Date localDate = new Date();

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
    }
}
