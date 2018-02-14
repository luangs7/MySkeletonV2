package br.com.luan.myskeletonv2.view.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem

import br.com.luan.myskeletonv2.R
import kotlinx.android.synthetic.main.activity_main.*

open class BaseDrawerActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        this.drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
