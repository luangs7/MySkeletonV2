package br.com.luan.myskeletonv2.view.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.*
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.R.color.drawer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseDrawerActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    fun initView(){
        //  <------------------  DRAWER MENU ------------------------>

        var drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.syncState()

        var navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        drawer.setScrimColor(Color.TRANSPARENT)
        drawer.drawerElevation = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            navigationView.setElevation(0f)
        }
        drawer.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {

                // Scale the View based on current slide offset
                val diffScaledOffset = slideOffset * (1 - 0.7f)
                val offsetScale = 1 - diffScaledOffset
                contentView?.setScaleX(offsetScale)
                contentView?.setScaleY(offsetScale)

                // Translate the View, accounting for the scaled width
                val xOffset = drawerView!!.width * slideOffset
                val xOffsetDiff = contentView!!.getWidth() * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                contentView?.setTranslationX(xTranslation)
            }

            override fun onDrawerClosed(drawerView: View?) {

            }
        }
        )

        //    <-------------------- END DRAWER ---------------------------->


//        startAnimationLaunch()
    }


    internal fun startAnimationLaunch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = drawer_layout.getMeasuredWidth() / 2
            val cy = drawer_layout.getMeasuredHeight() / 2

            val anim = ViewAnimationUtils.createCircularReveal(this.contentView, cx, cy, 50f, this.contentView!!.getWidth().toFloat())

            anim.duration = 1000
            anim.interpolator = AccelerateInterpolator(2f)
            anim.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)


                }
            })

            anim.start()
        }
    }
}
