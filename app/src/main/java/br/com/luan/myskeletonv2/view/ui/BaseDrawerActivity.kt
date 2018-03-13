package br.com.luan.myskeletonv2.view.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.view.ui.BottomAlert.BottomAlertActivity
import br.com.luan.myskeletonv2.view.ui.Expandable.ExpandableActivity
import br.com.luan.myskeletonv2.view.ui.FindMovie.MainActivity
import br.com.luan.myskeletonv2.view.ui.Products.ProductsActivity
import br.com.luan.myskeletonv2.view.ui.ShimmerList.ShimmerListActivity
import br.com.luan.myskeletonv2.view.ui.location.MapsActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseDrawerActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.home){
            startActivity(ProductsActivity())
        }
        else if(id == R.id.findmovie){
            startActivity(MainActivity())
        }
        else if(id == R.id.shimmer){
            startActivity(ShimmerListActivity())
        }
        else if(id == R.id.bottomAlert){
            startActivity(BottomAlertActivity())
        }
        else if(id == R.id.location){
            startActivity(MapsActivity())
        }
        else if(id == R.id.expandable){
            startActivity(ExpandableActivity())
        }

        this.drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
