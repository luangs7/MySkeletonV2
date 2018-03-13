package br.com.luan.myskeletonv2.view.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.view.ui.BottomAlert.BottomAlertActivity
import br.com.luan.myskeletonv2.view.ui.Expandable.ExpandableActivity
import br.com.luan.myskeletonv2.view.ui.FindMovie.MainActivity
import br.com.luan.myskeletonv2.view.ui.Parallax.ParallaxActivity
import br.com.luan.myskeletonv2.view.ui.Products.ProductsActivity
import br.com.luan.myskeletonv2.view.ui.ShimmerList.ShimmerListActivity
import br.com.luan.myskeletonv2.view.ui.Tabs.TabActivity
import br.com.luan.myskeletonv2.view.ui.location.MapsActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseDrawerActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = when (item.itemId) {

            R.id.home -> startActivity(ProductsActivity())
            R.id.findmovie -> startActivity(MainActivity())
            R.id.shimmer -> startActivity(ShimmerListActivity())
            R.id.bottomAlert -> startActivity(BottomAlertActivity())
            R.id.location -> startActivity(MapsActivity())
            R.id.expandable -> startActivity(ExpandableActivity())
            R.id.parallax -> startActivity(ParallaxActivity())
            R.id.tabs -> startActivity(TabActivity())
            else -> startActivity(MainActivity())
            }


        this.drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
