package com.waychel.app.layoutabovekeyboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends FragmentActivity {

    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = (RelativeLayout) findViewById(R.id.root_rl);
        //键盘显示，触摸键盘以外隐藏键盘
        findViewById(R.id.sv_content_rl).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View focus = root.findFocus();
                ScrollView sv = ((ScrollView) findViewById(R.id.sv));
                sv.fullScroll(ScrollView.FOCUS_DOWN);

                if (focus != null && focus instanceof EditText) {//保证滑动之后 焦点依然未变
                    focus.requestFocus();
                }

                int loc[] = new int[2];
                View view = findViewById(R.id.regist_area);
                view.getLocationOnScreen(loc);
                if (getScreenHeight(MainActivity.this) - loc[1] < Math.abs(getScreenHeight(MainActivity.this) / 2 - loc[1])) {//无压缩状态
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
