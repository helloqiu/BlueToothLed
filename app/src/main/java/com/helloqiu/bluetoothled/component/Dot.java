package com.helloqiu.bluetoothled.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by helloqiu on 15/12/3.
 */
public class Dot extends View {
    final static int MAX_ALPHA = 220;
    final static int MIN_ALPHA = 50;
    int alpha;
    Paint paint;
    Paint paint_water_wave;
    final static int WIDTH = 15;
    final static int TURN_ON = 0;
    final static int TURN_OFF = 1;
    int state;
    Context context;
    AttributeSet attributeSet;
    float width;
    float height;
    int alpha_water_wave;
    float radius_water_wave;
    float width_water_wave;
    float xDown_water_wave;
    float yDown_water_wave;
    int MAX_ALPHA_WATER_WAVE = 100;

    public Dot(Context context){
        this(context , null);
    }
    public Dot(Context context , AttributeSet attributeSet){
        super(context , attributeSet);
        this.context = context;
        this.attributeSet = attributeSet;
        alpha = MIN_ALPHA;
        state = TURN_OFF;

        alpha_water_wave = 0;
        radius_water_wave = 0.f;
        initPaint();
    }
    private void initPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
        paint.setAlpha(alpha);

        paint_water_wave = new Paint();
        paint_water_wave.setAntiAlias(true);
        paint_water_wave.setStrokeWidth(width_water_wave);

        paint_water_wave.setStyle(Paint.Style.STROKE);
        paint_water_wave.setAlpha(alpha_water_wave);
        paint_water_wave.setColor(Color.RED);
    }
    private void runWaterWave(){
        radius_water_wave = 0.f;
        alpha_water_wave = MAX_ALPHA_WATER_WAVE;
        width_water_wave = radius_water_wave / 4;
        handler.sendEmptyMessage(0);
        Log.e("Dot" , "water wave create");
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            switch (message.what){
                case 0:
                    flushState();
                    invalidate();
                    if (alpha_water_wave != 0) {
                        handler.sendEmptyMessageDelayed(0, 10);
                    }
            }
        }
        private void flushState(){
            radius_water_wave += 5.0f;
            alpha_water_wave -= 10;
            if (alpha_water_wave < 0){
                alpha_water_wave = 0;
            }
            width_water_wave = radius_water_wave / 4;
            paint_water_wave.setAlpha(alpha_water_wave);
            paint_water_wave.setStrokeWidth(width_water_wave);
        }
    };
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onDraw(Canvas canvas){
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        width = (this.getRight() - this.getLeft()) / 2 + this.getLeft();
        height = (this.getBottom() - this.getTop()) / 2 + this.getTop();
        canvas.drawCircle(width , height , WIDTH , paint);

        xDown_water_wave = width;
        yDown_water_wave = height;
        canvas.drawCircle(xDown_water_wave , yDown_water_wave , radius_water_wave , paint_water_wave);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                runWaterWave();
                if (state == TURN_OFF) {
                    state = TURN_ON;
                    alpha = MAX_ALPHA;
                    paint.setAlpha(alpha);
                    invalidate();
                    return true;
                } else {
                    state = TURN_OFF;
                    alpha = MIN_ALPHA;
                    paint.setAlpha(alpha);
                    invalidate();
                    return true;
                }
        }
        return true;
    }

}
