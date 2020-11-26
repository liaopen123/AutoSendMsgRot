package com.lph.autosendmsgrot.data

data class SMSInfo(var id:String,var thread_id:String,var smsAddress:String,var smsBody:String,var read:String,var action:Int) {
    constructor():this("","","","","",0)
}
