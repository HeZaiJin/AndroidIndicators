package com.haozhang.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.haozhang.widgets.LnkPageIndicator;

public class MainActivity extends AppCompatActivity {

    private com.haozhang.widgets.LnkPageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mIndicator = (LnkPageIndicator) findViewById(R.id.indicator);
        Button index2 = (Button) findViewById(R.id.index2);
        Button index1 = (Button) findViewById(R.id.index1);
        final Button index0 = (Button) findViewById(R.id.index0);
        index0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndicator.setSelectIndex(0);
            }
        });

        index1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndicator.setSelectIndex(1);
            }
        });

        index2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndicator.setSelectIndex(2);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
