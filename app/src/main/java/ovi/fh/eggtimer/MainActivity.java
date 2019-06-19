package ovi.fh.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int time;
    TextView textView;
    SeekBar seekBar;
    CountDownTimer countDownTimer;
    boolean counter;
    ImageView imageView;
    ImageView imageView2;
    public void resetTimer(){
        textView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        counter = false;

    }
    public void resetImage(){
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        imageView.animate().alpha(0).setDuration(200);
        imageView.setVisibility(View.INVISIBLE);

        imageView2.animate().alpha(1).setDuration(200);
        imageView2.setVisibility(View.VISIBLE);
    }
    public void resetImageFalse(){
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        imageView.animate().alpha(1).setDuration(200);
        imageView.setVisibility(View.VISIBLE);

        imageView2.animate().alpha(0).setDuration(200);
        imageView2.setVisibility(View.INVISIBLE);
    }

    public void timerButton(View view){
        if (counter){
            resetTimer();
            //resetImageFalse();

        }else {
            resetImageFalse();
            counter = true;
            textView = findViewById(R.id.textViewTimer);
            Log.i("TIme", Integer.toString(time));
            seekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    Log.i("second left!", String.valueOf(l / 1000));
                    updateTimer((int) l / 1000);
                }

                public void onFinish() {
                    textView.setText("0" + ":" + "00");
                    Log.i("we're done", "no more time");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    resetTimer();
                    resetImage();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if(seconds <= 9 ){
            secondString = "0" + seconds;
        }
        textView.setText(minutes + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewTimer);
        seekBar = findViewById(R.id.seekBartTImer);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                time = i;
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
}
