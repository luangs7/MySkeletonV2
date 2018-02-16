package br.com.luan.myskeletonv2.view.ui.FindMovie

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import java.util.ArrayList
import java.util.Arrays
import java.util.Timer
import java.util.TimerTask

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.RecyclerViewOnClickListenerHack
import br.com.luan.myskeletonv2.view.adapter.RecycleFilmeAdapter
import br.com.luan.myskeletonv2.view.adapter.SliderMainAdapter
import br.com.luan.myskeletonv2.view.ui.BaseActivity
import br.com.luan.myskeletonv2.view.ui.FindMovie.request.MainRequest
import br.com.squarebits.myskeletonv2.data.retrofit.ApiManager
import br.com.squarebits.myskeletonv2.data.retrofit.CustomCallback
import br.com.squarebits.myskeletonv2.data.retrofit.RequestInterface
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : BaseActivity() {


    internal var adapter: SliderMainAdapter?=null
    lateinit var adaptertodos: RecycleFilmeAdapter
    lateinit var adapterfavoritos: RecycleFilmeAdapter
    lateinit var adaptercurtidos: RecycleFilmeAdapter

    internal var handler = Handler()
    lateinit var timer: Timer

    public override fun onCreate(savedInstanceState: Bundle?) {

        //        Integer.parseInt("");
        //        FirebaseCrash.log("Activity created");

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
            toolbar.title = "Filmes"
            toolbar.setTitleTextColor(Color.parseColor("#2d2d2d"))
            setSupportActionBar(toolbar)



        maistodos!!.setOnClickListener { startActivity(Intent(baseContext, ListaProdutoActivity::class.java).putExtra("lista", "todos")) }

        listtodos!!.isFocusable = false


        maisfavoritos!!.setOnClickListener { startActivity(Intent(baseContext, ListaProdutoActivity::class.java).putExtra("lista", "favoritos")) }

        maiscurtidos!!.setOnClickListener { startActivity(Intent(baseContext, ListaProdutoActivity::class.java).putExtra("lista", "curtidos")) }


        adapter = SliderMainAdapter(baseContext, Arrays.asList(
                *arrayOf("http://br.web.img3.acsta.net/newsv7/16/08/17/16/21/191576.jpg", "https://i0.wp.com/media2.slashfilm.com/slashfilm/wp/wp-content/images/guardiansofthegalaxy2-teaserposter-frontpage.jpg", "https://i2.wp.com/www.seenit.co.uk/wp-content/uploads/The-Boss-Baby.jpg?fit=1200%2C422", "https://i.ytimg.com/vi/4xWOQrpTsBs/maxresdefault.jpg")))
        viewPager.adapter = adapter


        getpopular()
        getcurtidos()
        getfavoritos()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true
    }


    public override fun onStart() {
        super.onStart()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (viewPager.currentItem + 1 == adapter!!.count) {
                        viewPager.setCurrentItem(0, true)
                    } else {
                        viewPager.setCurrentItem(viewPager.currentItem + 1, true)
                    }
                }
            }
        }, 5000, 5000)

    }

    public override fun onStop() {
        super.onStop()
        timer.purge()
        timer.cancel()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId




        return super.onOptionsItemSelected(item)
    }

    fun getpopular() {
        ApiManager(baseContext,"http://api.themoviedb.org/3/movie/")
                .retrofit
                .create(RequestInterface::class.java)
                .getFilmesPopular(api_key, "1")
                .enqueue(CustomCallback(this, object : CustomCallback.OnResponse<MainRequest> {
                    override fun onResponse(response: MainRequest?) {
                        Log.d("", "onResponse: ")
                        popular(response!!.results)
                    }

                    override fun onFailure(t: Throwable?) {
                        Toast.makeText(baseContext, t!!.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onRetry(t: Throwable?) {
                        getpopular()
                    }
                }))

    }

    fun getcurtidos() {
        ApiManager(baseContext,"http://api.themoviedb.org/3/movie/")
                .retrofit
                .create(RequestInterface::class.java)
                .getFilmesVotados(api_key, "1")
                .enqueue(CustomCallback(this, object : CustomCallback.OnResponse<MainRequest> {
                    override fun onResponse(response: MainRequest?) {
                        Log.d("", "onResponse: ")

                        votados(response!!.results)

                    }

                    override fun onFailure(t: Throwable?) {
                        Toast.makeText(baseContext, t!!.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onRetry(t: Throwable?) {
                        getpopular()
                    }
                }))

    }

    fun getfavoritos() {
        val realm = Realm.getDefaultInstance()

        var favoritos: List<Filme> = ArrayList()
        favoritos = realm.where(Filme::class.java).findAll()

        if (favoritos.size > 0) {
            listfavoritos!!.setHasFixedSize(true)
            val llm = LinearLayoutManager(baseContext)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            listfavoritos!!.layoutManager = llm
            adapterfavoritos = RecycleFilmeAdapter(baseContext)
            listfavoritos!!.adapter = adapterfavoritos
            adapterfavoritos.recyclerViewOnClickListenerHack = object : RecyclerViewOnClickListenerHack {
                override fun onClickListener(view: View, `object`: Any, position: Int) {
                    val filme = `object` as Filme
                    startActivity(Intent(baseContext, DetalhesProdutosActivity::class.java)
                            .putExtra("filme", filme))
                }

                override fun onClickListener(view: View, position: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
            adapterfavoritos.replace(favoritos)
            relativefavoritos!!.visibility = View.VISIBLE
            listfavoritos!!.visibility = View.VISIBLE
        } else {
            relativefavoritos!!.visibility = View.GONE
            listfavoritos!!.visibility = View.GONE
        }

    }


    fun popular(todos: List<Filme>?) {
        listtodos!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(baseContext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        listtodos!!.layoutManager = llm
        adaptertodos = RecycleFilmeAdapter(baseContext)
        listtodos!!.adapter = adaptertodos
        adaptertodos.recyclerViewOnClickListenerHack = object : RecyclerViewOnClickListenerHack {
            override fun onClickListener(view: View, `object`: Any, position: Int) {
                val filme = `object` as Filme
                startActivity(Intent(baseContext, DetalhesProdutosActivity::class.java)
                        .putExtra("filme", filme))
            }

            override fun onClickListener(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        adaptertodos.replace(todos)
    }

    fun votados(todos: List<Filme>?) {
        listcurtidos!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(baseContext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        listcurtidos!!.layoutManager = llm
        adaptercurtidos = RecycleFilmeAdapter(baseContext)
        listcurtidos!!.adapter = adaptercurtidos
        adaptercurtidos.recyclerViewOnClickListenerHack = object : RecyclerViewOnClickListenerHack {
            override fun onClickListener(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onClickListener(view: View, `object`: Any, position: Int) {
                val filme = `object` as Filme
                startActivity(Intent(baseContext, DetalhesProdutosActivity::class.java)
                        .putExtra("filme", filme))
            }
        }
        adaptercurtidos.replace(todos)
    }

    override fun onRestart() {
        getfavoritos()
        super.onRestart()
    }

    override fun onResume() {
        getfavoritos()
        super.onResume()
    }

    companion object {

        internal val api_key = "b5127d1016c968d30de8d0d6cc725a73"
    }
}
