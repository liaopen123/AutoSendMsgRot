package com.lph.autosendmsgrot

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lph.autosendmsgrot.service.SMSService
import com.tbruyelle.rxpermissions3.RxPermissions

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var  rxPermissions =  RxPermissions(this);




        val myintent = Intent(this, SMSService::class.java)
        Log.i("smswatch", "Sms Start ")
        startService(myintent)
    }
}