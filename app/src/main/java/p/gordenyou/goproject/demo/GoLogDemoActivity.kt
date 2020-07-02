package p.gordenyou.goproject.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import p.gordenyou.golibrary.log.GoLog
import p.gordenyou.goproject.R

class GoLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_log_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener { printLog() }
    }

    private fun printLog(){
        GoLog.a("8899")
    }
}