package p.gordenyou.goproject.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import p.gordenyou.golibrary.log.*
import p.gordenyou.goproject.R

class GoLogDemoActivity : AppCompatActivity() {

    var viewPrinter:GoViewPrinter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_log_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener { printLog() }

        viewPrinter = GoViewPrinter(this)
        viewPrinter!!.printerProvider.showFloatingView()
    }

    private fun printLog(){
        GoLogManager.getInstance().addPrinter(viewPrinter)
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