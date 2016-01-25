package com.helloqiu.bluetoothled.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.helloqiu.bluetoothled.R;
import com.helloqiu.bluetoothled.component.Dot;

import java.lang.reflect.Method;

/**
 *
 * Created by helloqiu on 15/12/3.
 *
 */
public class MainActivity extends AppCompatActivity {

    Dot[][] dot;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        initLayout();
    }
    private void initLayout(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        int screenWidth = dm.widthPixels;
                        int screenHeight = dm.heightPixels;
                        float width = screenWidth / 16.f;
                        float height = (screenHeight - dip2px(getApplicationContext() , 100)) / 16.f;

                        LinearLayout linearLayout =(LinearLayout) findViewById(R.id.LinearLayout);
                        LinearLayout[] linearLayoutHeng = new LinearLayout[16];
                        LinearLayout[] linearLayoutsShu = new LinearLayout[16];
                        LinearLayout.LayoutParams layoutParamsHeng =
                                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , (int)height);
                        LinearLayout.LayoutParams layoutParamsShu =
                                new LinearLayout.LayoutParams((int)width , (int)height);
                        dot = new Dot[16][16];
                        for (int i = 0 ; i < 16 ; i ++){
                            linearLayoutHeng[i] = new LinearLayout(getApplicationContext());
                            linearLayoutHeng[i].setLayoutParams(layoutParamsHeng);
                            linearLayoutHeng[i].setOrientation(LinearLayout.HORIZONTAL);
                            for (int j = 0 ; j < 16 ; j ++){
                                linearLayoutsShu[j] = new LinearLayout(getApplicationContext());
                                linearLayoutsShu[j].setLayoutParams(layoutParamsShu);
                                dot[i][j] = new Dot(getApplicationContext());
                                dot[i][j].setWidth(screenWidth / 64);
                                linearLayoutsShu[j].addView(dot[i][j]);
                                linearLayoutHeng[i].addView(linearLayoutsShu[j]);
                            }
                            linearLayout.addView(linearLayoutHeng[i]);
                        }
                    }
                });
            }
        }).start();
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        setIconEnable(menu , true);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    private void setIconEnable(Menu menu, boolean enable){
        try{
            Class<?> clazz = Class.forName("android.support.v7.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            m.invoke(menu, enable);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_turn_on_all:
                // turn on all
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0 ; i < 16 ; i ++) {
                                    for (int j = 0; j < 16; j++) {
                                        dot[i][j].setState(Dot.TURN_ON);
                                    }
                                }
                            }
                        });
                    }
                }).start();
                break;
            case R.id.main_menu_turn_off_all:
                //turn off all
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0 ; i < 16 ; i ++){
                                    for (int j = 0 ; j < 16 ; j++){
                                        dot[i][j].setState(Dot.TURN_OFF);
                                    }
                                }
                            }
                        });
                    }
                }).start();
                break;
            case R.id.main_menu_send:
                //send
                //TODO : send the data to bluetooth
                break;
            case R.id.main_menu_exit:
                // exit
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale +0.5f);
    }
}
