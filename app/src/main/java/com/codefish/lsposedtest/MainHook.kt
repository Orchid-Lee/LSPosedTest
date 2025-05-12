package com.codefish.lsposedtest

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: LoadPackageParam?) {
        if (lpparam != null) {
            hook(lpparam)
        }
    }

    private fun hook(lpparam: LoadPackageParam) {
        // 具体流程
        XposedBridge.log("Tlpysl: " + lpparam.packageName);
    }

}