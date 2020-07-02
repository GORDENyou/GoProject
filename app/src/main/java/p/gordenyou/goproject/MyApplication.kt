package p.gordenyou.goproject

import android.app.Application
import p.gordenyou.golibrary.log.GoLogConfig
import p.gordenyou.golibrary.log.GoLogManager

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        GoLogManager.init( object : GoLogConfig(){
            override fun getGlobalTag(): String {
                return "GoApplication"
            }

            override fun enable(): Boolean {
                return true
            }
        })
    }
}