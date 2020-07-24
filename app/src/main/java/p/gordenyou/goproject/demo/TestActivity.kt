package p.gordenyou.goproject.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import p.gordenyou.goproject.R
import p.gordenyou.goproject.demo.tab.GoTabBottomDemoActivity

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

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