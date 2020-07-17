package p.gordenyou.goproject.demo.tab

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import p.gordenyou.golibrary.util.GoDisplayUtil
import p.gordenyou.goproject.R
import p.gordenyou.goui.tab.bottom.GoTabBottomInfo
import p.gordenyou.goui.tab.bottom.GoTabBottomLayout

class GoTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_tab_bottom_demo)
        initTabBottom()
    }

    private fun initTabBottom() {
        val goTabBottomLayout: GoTabBottomLayout = findViewById(R.id.gotablayout)
        goTabBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList: MutableList<GoTabBottomInfo<*>> = ArrayList()
        val homeInfo = GoTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoRecommend = GoTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            "#ff656667",
            "#ffd44949"
        )

//        val infoCategory = GoTabBottomInfo(
//            "分类",
//            "fonts/iconfont.ttf",
//            getString(R.string.if_category),
//            null,
//            "#ff656667",
//            "#ffd44949"
//        )
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.fire, null)

        val infoCategory = GoTabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )
        val infoChat = GoTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoProfile = GoTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )

        bottomInfoList.add(homeInfo)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoCategory)
        bottomInfoList.add(infoChat)
        bottomInfoList.add(infoProfile)
        goTabBottomLayout.inflateInfo(bottomInfoList)
//        Handler().postDelayed(Runnable {
//            infoList.removeAt(1)
//            hiTabBottomLayout.inflateInfo(infoList)
//            hiTabBottomLayout.defaultSelected(homeInfo)
//        },2000)
        goTabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this@GoTabBottomDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
        }

        goTabBottomLayout.defaultSelected(homeInfo)

        val tabBottom = goTabBottomLayout.findTab(bottomInfoList[2])
        tabBottom?.apply { resetHeight(GoDisplayUtil.dp2px(66f, resources)) }
    }

}