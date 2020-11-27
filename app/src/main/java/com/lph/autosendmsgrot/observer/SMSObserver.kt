package com.lph.autosendmsgrot.observer

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.lph.autosendmsgrot.data.DingTalkBean
import com.lph.autosendmsgrot.data.SMSInfo
import com.lph.autosendmsgrot.ext.getVerifyCode
import com.lph.autosendmsgrot.handler.SMSHandler
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import okhttp3.MediaType
import okhttp3.RequestBody


class SMSObserver(var resolver: ContentResolver, var handler: SMSHandler, var context: Context) :
    ContentObserver(
        handler
    ) {

    var lastMsgId="0"
    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)

        val cursor: Cursor = resolver.query(
            Uri.parse("content://sms/inbox"),
            arrayOf("_id", "address", "read", "body", "thread_id"),
            "read=?",
            arrayOf("0"),
            "date desc"
        )!!

        if (cursor == null) {
            return
        } else {
            val smsinfo = SMSInfo()
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex("_id")
                if (idIndex != -1) {
                    smsinfo.id = cursor.getString(idIndex)
                }
                val threadIndex = cursor.getColumnIndex("thread_id")
                if (threadIndex != -1) {
                    smsinfo.thread_id = cursor.getString(threadIndex)
                }
                val addressIndex = cursor.getColumnIndex("address")
                if (addressIndex != -1) {
                    smsinfo.smsAddress = cursor.getString(addressIndex)
                }
                val bodyIndex = cursor.getColumnIndex("body")
                if (bodyIndex != -1) {
                    smsinfo.smsBody = cursor.getString(bodyIndex)
                }
                val readIndex = cursor.getColumnIndex("read")
                if (readIndex != -1) {
                    smsinfo.read = cursor.getString(readIndex)
                }
                Log.i("得到短信:", smsinfo.toString())

                if (smsinfo.id!=lastMsgId&&smsinfo.smsBody.contains("百度") && smsinfo.smsBody.contains("验证码")) {
                    lastMsgId = smsinfo.id
                    val dingTalkBean = DingTalkBean()
                    dingTalkBean.link.apply {
                        title = "百度网盘验证码为: ${smsinfo.smsBody.getVerifyCode(6)}"
                        text = smsinfo.smsBody
                    }
                    val toJson = Gson().toJson(dingTalkBean)
                    val body: RequestBody = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        toJson
                    )
                    OkGo.post<String>("https://oapi.dingtalk.com/robot/send?access_token=c40f4b807608e79fea612a9afb669a38325b1671282db7f4d6c7e1c02fba6c61") //
                        .tag(this) //
                        .upRequestBody(body)
                        .execute(object : StringCallback() {
                            override fun onSuccess(response: Response<String?>?) {
                                Log.e("response", "onSuccess");
                            }

                            override fun onError(response: Response<String?>?) {
                                Log.e("response", "onError");
                            }
                        })



                    //既时达
                    OkGo.get<String>("http://push.ijingniu.cn/send?key=821007408db0428684b9e4838aceff1a&head="+"百度网盘验证码为: ${smsinfo.smsBody.getVerifyCode(6)}"+"&body="+smsinfo.smsBody)
                        .tag(this) //
                        .execute(object : StringCallback() {
                            override fun onSuccess(response: Response<String?>?) {
                                Log.e("response", "onSuccess");
                            }

                            override fun onError(response: Response<String?>?) {
                                Log.e("response", "onError");
                            }
                        })
                }
            }

        }


    }


}