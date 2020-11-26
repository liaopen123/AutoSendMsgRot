package com.lph.autosendmsgrot.data

class DingTalkBean {
    var msgtype:String = "link"
    var link:Link =Link()



    inner class Link{
        var title:String =""
        var text:String=""
        var picUrl:String ="https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u=3668403230,1853690655&fm=74&app=80&f=PNG&size=f121,121?sec=1880279984&t=c17589a86df413adb427eb81f1b4d2df"
        var messageUrl:String="https://www.baidu.com/"
    }
}