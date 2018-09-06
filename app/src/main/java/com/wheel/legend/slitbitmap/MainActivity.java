package com.wheel.legend.slitbitmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    SlitImageView slitImageView;

    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slitImageView=findViewById(R.id.slit);

        slitImageView.setExpand(SlitImageView.EXPAND_TO_BOTTOM);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float dx=0,dy=0,rx,ry;

        int screenWidth=getResources().getDisplayMetrics().widthPixels;

        int screenHeight=getResources().getDisplayMetrics().heightPixels;


        switch (event.getAction()){


            case MotionEvent.ACTION_DOWN:

                dx=event.getRawX();
                dy=event.getRawY();

//                Log.d("dy--->>>",dx+"");

//                int ww= (int) (screenWidth-dx);
//
//                slitImageView.change(ww);

//                slitImageView.change((int) dx);

                slitImageView.change((int) dy);

                break;



            case MotionEvent.ACTION_MOVE:

                int w= (int) (event.getRawX()-dx);

                int h= (int) (event.getRawY()-dy);

//                int rw=screenWidth-w;
//
//                slitImageView.change(rw);

//                slitImageView.change(w);

                slitImageView.change(h);


                break;



            case MotionEvent.ACTION_UP:
                break;



        }



        return super.onTouchEvent(event);
    }
}
