package com.buxiaohui.doubleclick;



import com.buxiaohui.annotation.FDC;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @FDC(timeInterval = 1000)
            @Override
            public void onClick(View v) {
                System.out.println(TAG + "-----method--onClick，button 1");
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @FDC(tag = "routeResult",timeInterval = 1000)
            @Override
            public void onClick(View v) {
                System.out.println(TAG + "-----method--onClick，button 2");
            }
        });
    }
}