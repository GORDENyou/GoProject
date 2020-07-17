package p.gordenyou.goproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import p.gordenyou.goproject.demo.GoLogDemoActivity
import p.gordenyou.goproject.demo.tab.GoTabBottomDemoActivity
import p.gordenyou.goui.tab.bottom.GoTabBottom
import p.gordenyou.goui.tab.bottom.GoTabBottomInfo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_hilog -> {
                startActivity(Intent(this, GoLogDemoActivity::class.java))
            }
            R.id.tv_bottom -> {
                startActivity(Intent(this, GoTabBottomDemoActivity::class.java))
            }
        }
    }
}