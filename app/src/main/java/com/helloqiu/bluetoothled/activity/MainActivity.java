package com.helloqiu.bluetoothled.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.helloqiu.bluetoothled.R;
import com.helloqiu.bluetoothled.component.Dot;
import com.yalantis.contextmenu.lib.MenuObject;

/**
 * Created by helloqiu on 15/12/3.
 */
public class MainActivity extends AppCompatActivity {

    Dot[][] dot;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);

        initLayout();
    }
    private void initLayout(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float width = screenWidth / 16.f;
        float height = (screenHeight - dip2px(this.getApplicationContext() , 100)) / 16.f;

        LinearLayout linearLayout =(LinearLayout) this.findViewById(R.id.LinearLayout);
        LinearLayout[] linearLayoutHeng = new LinearLayout[16];
        LinearLayout[] linearLayoutsShu = new LinearLayout[16];
        LinearLayout.LayoutParams layoutParamsHeng =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , (int)height);
        LinearLayout.LayoutParams layoutParamsShu =
                new LinearLayout.LayoutParams((int)width , (int)height);
        dot = new Dot[16][16];
        for (int i = 0 ; i < 16 ; i ++){
            linearLayoutHeng[i] = new LinearLayout(this.getApplicationContext());
            linearLayoutHeng[i].setLayoutParams(layoutParamsHeng);
            linearLayoutHeng[i].setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0 ; j < 16 ; j ++){
                linearLayoutsShu[j] = new LinearLayout(this.getApplicationContext());
                linearLayoutsShu[j].setLayoutParams(layoutParamsShu);
                dot[i][j] = new Dot(this.getApplicationContext());
                linearLayoutsShu[j].addView(dot[i][j]);
                linearLayoutHeng[i].addView(linearLayoutsShu[j]);
            }
            linearLayout.addView(linearLayoutHeng[i]);
        }
    }
    private void initMenu(){
        
    }
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale +0.5f);
    }
}
