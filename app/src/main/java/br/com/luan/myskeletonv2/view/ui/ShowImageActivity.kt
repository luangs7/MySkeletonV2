package br.com.luan.myskeletonv2.view.ui

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.data.model.Product
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_main.*


import java.io.File

class ShowImageActivity : BaseActivity() {

    internal var viewPager:ViewPager? = null
    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        setSupportActionBar(this.toolbar)



        product = intent.getParcelableExtra<Product>("product")


        viewPager = findViewById(R.id.viewPager) as ViewPager

        viewPager!!.adapter = SamplePagerAdapter(product.images!!,this)

    }

    override fun onResume() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        menuInflater.inflate(R.menu.menu_photos, menu)

        return true

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val id = when(item.itemId){
            android.R.id.home ->  onBackPressed()
            R.id.download -> download(product.images!!.get(viewPager!!.currentItem),viewPager!!.currentItem)
            else -> return true

        }


        return super.onOptionsItemSelected(item)

    }

    fun download(url:String, position:Int) {
        try {
            val filename = product.name + position + ".jpg"
            val downloadUrlOfImage = url
            val direct = File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .absolutePath + "/" + getString(R.string.app_name) + "/")


            if (!direct.exists()) {
                direct.mkdir()
                Log.d("debug", "dir created for first time")
            }

            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                            File.separator + getString(R.string.app_name) + File.separator + filename)

            dm.enqueue(request)
            val dir = Environment.DIRECTORY_PICTURES +
                    File.separator +  getString(R.string.app_name) + File.separator + filename
            Toast.makeText(this, "Imagem salva em: " + dir, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao salvar imagem", Toast.LENGTH_SHORT).show()

        }

    }
}




    internal class SamplePagerAdapter(sDrawables:List<String>, context:Context) : PagerAdapter() {

        var sDrawables:List<String>? = null
        var context: Context? = null

        init {
            this.context = context
            this.sDrawables = sDrawables
        }

        override fun getCount(): Int {
            return sDrawables!!.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            val photoView = PhotoView(container.context)
            Picasso.with(context).load(sDrawables!!.get(position)).centerInside().fit().into(photoView)

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }



    }



