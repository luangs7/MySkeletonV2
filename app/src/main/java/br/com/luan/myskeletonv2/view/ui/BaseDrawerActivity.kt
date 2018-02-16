package br.com.luan.myskeletonv2.view.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.view.ui.FindMovie.MainActivity
import br.com.luan.myskeletonv2.view.ui.Products.ProductsActivity
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
        if(id == R.id.findmovie){
            startActivity(MainActivity())
        }

        this.drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
