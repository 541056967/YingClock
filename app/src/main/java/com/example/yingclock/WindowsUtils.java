package com.example.yingclock;

import android.content.res.Resources;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class WindowsUtils {
    public static void setStatusBarColor(Window window, Resources resources, int id){
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(resources.getColor(id));
    }
}
