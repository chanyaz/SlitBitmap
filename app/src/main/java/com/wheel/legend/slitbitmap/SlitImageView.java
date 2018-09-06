package com.wheel.legend.slitbitmap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;


public class SlitImageView extends FrameLayout {

    private Bitmap bitmap;
    private int limit;
    private boolean isScale=false;
    private Paint paint;
    private int alpha=255;
    private int expand=EXPAND_TO_BOTTOM;


    public static final int EXPAND_TO_LEFT=100;
    public static final int EXPAND_TO_RIGHT=200;
    public static final int EXPAND_TO_BOTTOM=300;
    public static final int EXPAND_TO_TOP=400;

    public SlitImageView(Context context) {
        super(context);

    }

    public SlitImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);



        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.SlitImageView);

        int id=typedArray.getResourceId(R.styleable.SlitImageView_srcImage,-1);

        this.bitmap=drawable2Bitmap(id);

        this.limit =typedArray.getInteger(R.styleable.SlitImageView_limitHeight,-1);

        typedArray.recycle();

        scaleBitmap();

    }

    public SlitImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (this.limit <=0){

            switch (this.expand){

                case EXPAND_TO_BOTTOM:
                case EXPAND_TO_RIGHT:

                    this.limit =getMeasuredHeight();

                    break;


                case EXPAND_TO_LEFT:
                case EXPAND_TO_TOP:

                    this.limit =0;

                    break;


            }


        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap==null) {
            super.onDraw(canvas);
        }else {

            initPaint();

            switch (this.expand){

                case EXPAND_TO_BOTTOM://向下展开

                    canvas.clipRect(0,0,bitmap.getWidth(), limit);

                    canvas.drawBitmap(bitmap,0,0,paint);


                    break;


                case EXPAND_TO_LEFT://向左展开

                    canvas.clipRect(limit,0,bitmap.getWidth(), bitmap.getHeight());

                    int s=limit-bitmap.getWidth();

                    if (s<0){
                        s=0;
                    }

                    if (s>bitmap.getWidth()){
                        s=bitmap.getWidth();
                    }

                    canvas.drawBitmap(bitmap,s,0,paint);

                    break;


                case EXPAND_TO_RIGHT://向右展开

                    canvas.clipRect(0,0,limit, bitmap.getHeight());

                    canvas.drawBitmap(bitmap,0,0,paint);

                    break;


                case EXPAND_TO_TOP://向上展开

                    canvas.clipRect(0,limit,bitmap.getWidth(), bitmap.getHeight());

                    int h=limit-bitmap.getHeight();
                    if (h<0){
                        h=0;
                    }

                    if (h>bitmap.getHeight()){
                        h=bitmap.getHeight();
                    }

                    canvas.drawBitmap(bitmap,0,h,paint);

                    break;


            }
        }
    }


    private Bitmap drawable2Bitmap(int id){

        if (id==-1){
            return null;
        }

        return BitmapFactory.decodeResource(getResources(),id);

    }



    public void change(int limit){

        this.limit =limit;

        postInvalidate();//刷新
    }

    private void scaleBitmap(){

        if (isScale){
            return;
        }

        if (this.bitmap!=null){

            if (bitmap.getHeight()>getMeasuredHeight()||bitmap.getWidth()>getMeasuredWidth()){//图片本身高度大于view


                getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        Matrix matrix=new Matrix();
                        double scaleX=1.0*getWidth()/bitmap.getWidth();

                        double scaleY=1.0*getHeight()/bitmap.getHeight();

                        matrix.preScale((float) scaleX,(float) scaleY);

                        bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

                        isScale=true;

                        setBackgroundColor(Color.TRANSPARENT);

                    }
                });

            }
        }

    }


    private void initPaint(){

        if (paint==null){
            paint=new Paint();
            paint.setAntiAlias(true);
        }

        paint.setAlpha(alpha);

    }

    public void setAlpha(int alpha) {

        if (alpha<0){
            alpha=0;
        }

        if (alpha>255){
            alpha=255;
        }

        this.alpha = alpha;

    }


    public void setExpand(int expand) {
        this.expand = expand;
    }
}
