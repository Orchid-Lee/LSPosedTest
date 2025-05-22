package com.codefish.lsposedtest

import android.app.Activity
import android.os.Bundle
import com.codefish.lsposedtest.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : Activity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.apply {

            if (isRooted){
                status.text = "模块已Root"
            }else{
                status.text = "模块未Root"
            }

            if (isModuleActive()) {
                status.text = "${status.text}, 模块已激活!"
            }
        }
    }

    /**
     * 模块是否激活
     */
    private fun isModuleActive(): Boolean {
        return false
    }

    /**
     * 检查是否root
     */
    private val isRooted: Boolean by lazy {
        try {
            val process = ProcessBuilder("su", "-c", "cat /system/build.prop").start()
            process.waitFor() == 0
        } catch (e: IOException) {
            false
        } catch (e: InterruptedException) {
            false
        }
    }
}