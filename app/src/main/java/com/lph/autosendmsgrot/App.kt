package com.lph.autosendmsgrot

import android.app.Application
import com.lzy.okgo.OkGo

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        OkGo.getInstance().init(this);
    }
}