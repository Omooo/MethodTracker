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

        test();

        new InlineClass().doSth();
    }

    private void test() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class InlineClass {

        void doSth() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
