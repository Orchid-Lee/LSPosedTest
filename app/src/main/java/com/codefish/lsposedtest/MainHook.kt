package com.codefish.lsposedtest

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {

        //检测是否激活
        if(lpparam.packageName.equals("com.codefish.lsposedtest")){
            XposedHelpers.findAndHookMethod("com.codefish.lsposedtest.MainActivity", lpparam.classLoader, "isModuleActive", XC_MethodReplacement.returnConstant(true))
        }

        //去除频繁安装校验
        var targetMethod = XposedHelpers.findMethodExact(
            XposedHelpers.findClass(
                "com.oplus.appdetail.model.entrance.ChannelBarrageActivity",
                lpparam.classLoader
            ),
            "x0",
            *arrayOf<Class<*>>()
        )
        XposedBridge.hookMethod(targetMethod, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                val instance = param?.thisObject
                if (instance != null) {
                    try {
                        val replacement = XposedHelpers.findMethodExact(
                            XposedHelpers.findClass(
                                "com.oplus.appdetail.model.entrance.ChannelBarrageActivity",
                                lpparam.classLoader
                            ),
                            "",
                            *arrayOf<Class<*>>()
                        )
                        replacement.invoke(instance) // 调用替换方法
                        param.result = null // 对于 void 方法，设置 result 为 null 阻止原始方法继续执行
                        // 或者，你也可以抛出一个新的 Throwable 来阻止原始方法继续执行
                        // throw Throwable("Method replaced by Xposed module")
                    } catch (e: Exception) {
                        XposedBridge.log("Error calling replacement method: $e")
                    }
                } else {
                    XposedBridge.log("Instance is null in hooked method.")
                }
            }
        })
        //去除风险扫描
        targetMethod = XposedHelpers.findMethodExact(
            XposedHelpers.findClass(
                "com.oplus.appdetail.model.guide.viewModel.GuideShareViewModel",
                lpparam.classLoader
            ),
            "i",
            *arrayOf<Class<*>>()
        )

        XposedBridge.hookMethod(targetMethod, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                // 原始代码中使用了 before 代码块，对应 Xposed 的 beforeHookedMethod
                // 原始代码设置 result = true，对应 Xposed 的 param.result = true
                param?.result = true
            }
        })
    }
}