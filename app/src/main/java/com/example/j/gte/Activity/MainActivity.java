package com.example.j.gte.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.j.gte.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String showMap=null;
    private String myplan=null;
    private String myCollection=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        showMap=getResources().getString(R.string.show_map);
        myplan=getResources().getString(R.string.my_plan);
        myCollection=getResources().getString(R.string.my_colection);

        SpannableStringBuilder SpannableSM=
                highlight(showMap, Color.BLACK,Color.GRAY, 40,30);
        SpannableStringBuilder SpannableMP=
                highlight(myplan, Color.BLACK,Color.GRAY,40,30);
        SpannableStringBuilder SpannableMC=
                highlight(myCollection, Color.BLACK,Color.GRAY,40,30);


        Button Show_Map=(Button)findViewById(R.id.Show_Map);
        Button My_Plan=(Button)findViewById(R.id.My_Plan);
        Button My_Collection=(Button)findViewById(R.id.My_Collection);

        Show_Map.setOnClickListener(this);
        My_Collection.setOnClickListener(this);
        My_Plan.setOnClickListener(this);


        Show_Map.setText(SpannableSM);
        My_Plan.setText(SpannableMP);
        My_Collection.setText(SpannableMC);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Show_Map:
                break;

            case  R.id.My_Plan:
                break;

            case  R.id.My_Collection:
                break;

            default:
                break;
        }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public SpannableStringBuilder highlight(String text,int color1,int color2,int fontSize1,
                                            int fontSize2){
        SpannableStringBuilder spannable=new SpannableStringBuilder(text);//用于可变字符串
        CharacterStyle span_0=null,span_1=null,span_2,span_3;
        int end=text.indexOf("\n");
        if(end==-1){//如果没有换行符就使用第一种颜色显示
            span_0=new ForegroundColorSpan(color1);
            spannable.setSpan(span_0, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else{
            span_0=new ForegroundColorSpan(color1);
            span_1=new ForegroundColorSpan(color2);

            spannable.setSpan(span_0, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(span_1, end+1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span_2=new AbsoluteSizeSpan(fontSize1);
            span_3=new AbsoluteSizeSpan(fontSize2);//字体大小
            spannable.setSpan(span_2,0, end,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(span_3, end+1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }
}
