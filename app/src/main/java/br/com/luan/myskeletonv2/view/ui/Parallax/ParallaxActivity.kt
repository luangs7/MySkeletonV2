package br.com.luan.myskeletonv2.view.ui.Parallax

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.View

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import kotlinx.android.synthetic.main.activity_parallax.*

class ParallaxActivity : BaseDrawerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallax)
        setSupportActionBar(toolbar)

        val mAppBarLayout = findViewById(R.id.app_bar) as AppBarLayout
        mAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    imageProfile.visibility = View.GONE
                    toolbar.title = "Perfil"
                    toolbar.setTitleTextColor(Color.WHITE)

                } else if (isShow) {
                    imageProfile.visibility = View.VISIBLE
                    toolbar.title = " "
                    isShow = false
                }
            }
        })
    }
}
