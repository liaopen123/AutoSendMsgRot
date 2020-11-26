package com.lph.autosendmsgrot.service

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.os.Process
import android.util.Log
import com.lph.autosendmsgrot.handler.SMSHandler
import com.lph.autosendmsgrot.observer.SMSObserver

class SMSService :Service() {
    private var mBinder: MyBinder? = null
    private var sobr: SMSObserver? = null

    override fun onBind(p0: Intent?): IBinder? {
        mBinder = MyBinder()
        return mBinder!!
    }

    override fun onCreate() {
        Log.i("smswatch", "Sms Service Create  ")
        val resolver = contentResolver
        sobr = SMSObserver(resolver, SMSHandler(this),this)
        resolver.registerContentObserver(Uri.parse("content://sms"), true, sobr!!)
    }


    override fun onDestroy() {
        Log.i("smswatch", "Sms Service Destroy ")
        super.onDestroy()
        this.contentResolver.unregisterContentObserver(sobr!!)
        Process.killProcess(Process.myPid())
    }


    inner class MyBinder :Binder() {

    }

}