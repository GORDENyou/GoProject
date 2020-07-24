package p.gordenyou.goproject.demo

import android.app.Application
import com.google.gson.Gson
import p.gordenyou.golibrary.log.GoConsolePrinter
import p.gordenyou.golibrary.log.GoLogConfig
import p.gordenyou.golibrary.log.GoLogManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GoLogManager.init(object : GoLogConfig() {
            override fun injectJsonParser(): JsonParser {
                // 将 Gson 中的转换器传入
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalTag(): String {
                return "GoApplication"
            }

            override fun enable(): Boolean {
                return true
            }
        }, GoConsolePrinter())
    }
}