package com.haozhang.indicator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.haozhang.widgets.LnkPageIndicator;

public class MainActivity extends AppCompatActivity {

    private com.haozhang.widgets.LnkPageIndicator indicator;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LnkPageIndicator indicator = (LnkPageIndicator) findViewById(R.id.indicator);
        Button bt = (Button) findViewById(R.id.bt);
        this.indicator = (LnkPageIndicator) findViewById(R.id.indicator);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index >=indicator.getMax()){
                    index = 0;
                }
                indicator.setSelectIndex(index);
            }
        });
    }
}
