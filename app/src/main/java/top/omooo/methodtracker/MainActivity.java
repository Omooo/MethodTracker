package top.omooo.methodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import top.omooo.annotation.MethodTrack;

@MethodTrack
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
