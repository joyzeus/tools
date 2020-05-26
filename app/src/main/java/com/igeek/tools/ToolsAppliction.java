package com.igeek.tools;

import android.app.Application;
import android.util.Log;

import com.igeek.tools.db.DataManager;
import com.igeek.tools.db.entity.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ToolsAppliction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initObjectBox();
    }

    private BoxStore boxStore;

    private void initObjectBox() {
        Log.i("ObjectBrowser", "initObjectBox: ");
        boxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {//开启浏览器访问ObjectBox
            boolean started = new AndroidObjectBrowser(boxStore).start(this);
            Log.i("ObjectBrowser", "Started: " + started);
        }
        DataManager.getInstance().init(this);//数据库统一操作管理类初始化
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
