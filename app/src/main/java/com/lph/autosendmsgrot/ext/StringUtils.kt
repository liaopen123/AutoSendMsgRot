package com.lph.autosendmsgrot.ext

import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.getVerifyCode(length: Int):String{

//	获得数字字母组合
//    Pattern p = Pattern   .compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + YZMLENGTH + "})(?![a-zA-Z0-9])");

//	获得纯数字
    // 首先([a-zA-Z0-9]{YZMLENGTH})是得到一个连续的六位数字字母组合
    // (?<![a-zA-Z0-9])负向断言([0-9]{YZMLENGTH})前面不能有数字
    // (?![a-zA-Z0-9])断言([0-9]{YZMLENGTH})后面不能有数字出现


//	获得数字字母组合
//    Pattern p = Pattern   .compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + YZMLENGTH + "})(?![a-zA-Z0-9])");

//	获得纯数字
    val p: Pattern = Pattern.compile("(?<![0-9])([0-9]{$length})(?![0-9])")

    val m: Matcher = p.matcher(this)
    if (m.find()) {
        System.out.println(m.group())
        return m.group(0)
    }
    return ""
}