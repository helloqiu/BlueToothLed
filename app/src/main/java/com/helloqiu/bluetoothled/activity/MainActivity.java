package com.helloqiu.bluetoothled.activity;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.helloqiu.bluetoothled.R;
import com.helloqiu.bluetoothled.component.Dot;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helloqiu on 15/12/3.
 */
public class MainActivity extends AppCompatActivity {

    Dot[][] dot;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        fragmentManager = getSupportFragmentManager();
        initMenu();
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
                dot[i][j].setWidth(screenWidth / 64);
                linearLayoutsShu[j].addView(dot[i][j]);
                linearLayoutHeng[i].addView(linearLayoutsShu[j]);
            }
            linearLayout.addView(linearLayoutHeng[i]);
        }
    }
    private void initMenu(){
        MenuObject turnOn = new MenuObject(getResources().getString(R.string.turn_on_all));
        turnOn.setResource(R.drawable.icon_turn_on);
        turnOn.setBgColor(Color.TRANSPARENT);

        MenuObject turnOff = new MenuObject(getResources().getString(R.string.turn_off_all));
        turnOff.setResource(R.drawable.icon_turn_off);
        turnOff.setBgColor(Color.TRANSPARENT);

        MenuObject send = new MenuObject(getResources().getString(R.string.send));
        send.setResource(R.drawable.icon_bluetooth);
        send.setBgColor(Color.TRANSPARENT);

        MenuObject exit = new MenuObject(getResources().getString(R.string.exit));
        exit.setResource(R.drawable.icon_exit);
        exit.setBgColor(Color.TRANSPARENT);

        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(turnOn);
        menuObjects.add(turnOff);
        menuObjects.add(send);
        menuObjects.add(exit);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;

        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize(screenHeight / 12);
        menuParams.setMenuObjects(menuObjects);
        menuParams.setClosableOutside(true);
        mMenuDialogFragment = ContextMenuDialogFragment
                .newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(listener);
    }
    private OnMenuItemClickListener listener = new OnMenuItemClickListener() {
        @Override
        public void onMenuItemClick(View clickedView, int position) {
            switch (position){
                case 0:
                    // turn on all
                    for (int i = 0 ; i < 16 ; i ++){
                        for (int j = 0 ; j < 16 ; j++){
                            dot[i][j].setState(Dot.TURN_ON);
                        }
                    }
                    break;
                case 1:
                    //turn off all
                    for (int i = 0 ; i < 16 ; i ++){
                        for (int j = 0 ; j < 16 ; j++){
                            dot[i][j].setState(Dot.TURN_OFF);
                        }
                    }
                    break;
                case 2:
                    //send
                    //TODO: Send data to bluetooth

                    break;
                case 3:
                    // exit
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale +0.5f);
    }
}
