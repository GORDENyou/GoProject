package p.gordenyou.goproject.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import p.gordenyou.golibrary.log.GoLog
import p.gordenyou.golibrary.log.GoLogConfig
import p.gordenyou.golibrary.log.GoLogType
import p.gordenyou.goproject.R

class GoLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_log_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener { printLog() }
    }

    private fun printLog(){
        GoLog.log(object : GoLogConfig(){
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, GoLogType.E, "------", "5566")

        GoLog.a("8899")
    }
}