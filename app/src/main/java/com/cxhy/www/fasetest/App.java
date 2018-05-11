package com.cxhy.www.fasetest;

import android.app.Application;
import org.xutils.x;
/**
 * Created by yanle on 2018/4/21.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
