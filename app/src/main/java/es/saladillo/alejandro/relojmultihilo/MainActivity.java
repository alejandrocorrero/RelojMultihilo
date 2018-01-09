package es.saladillo.alejandro.relojmultihilo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.lblClock)
    TextView lblclock;
    @BindView(R.id.btnStart)
    Button btnStart;
    private Thread secundaryThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
    lblclock.setText(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
    }
    @OnClick(R.id.btnStart)
    public void clickBtn(){
        secundaryThread = new Thread(new Clock());
        secundaryThread.start();
        if(secundaryThread.isAlive())
            secundaryThread.interrupt();
        btnStart.setText(getResources().getString(R.string.mainactivity_txtstop));
    }


    private class Clock implements Runnable {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){

                final String time = simpleDateFormat.format(new Date());
                Runnable task = () -> updateTime(time);
                runOnUiThread(task);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    private void updateTime(String time) {
        lblclock.setText(time);
    }

}
