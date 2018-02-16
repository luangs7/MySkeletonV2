package br.com.luan.myskeletonv2.view.ui.FindMovie

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast

import java.util.ArrayList

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.EndlessRecyclerViewScrollListener
import br.com.luan.myskeletonv2.extras.RecyclerViewOnClickListenerHack
import br.com.luan.myskeletonv2.view.adapter.RecycleFilmeListaAdapter
import br.com.luan.myskeletonv2.view.ui.BaseActivity
import br.com.luan.myskeletonv2.view.ui.FindMovie.request.MainRequest
import br.com.luan.myskeletonv2.view.ui.MainActivity
import br.com.squarebits.myskeletonv2.data.retrofit.ApiManager
import br.com.squarebits.myskeletonv2.data.retrofit.CustomCallback
import br.com.squarebits.myskeletonv2.data.retrofit.RequestInterface
import io.realm.Realm
import io.realm.RealmConfiguration

class ListaProdutoActivity : BaseActivity() {

    private var toolbar: Toolbar? = null
    private var appBarLayout: AppBarLayout? = null
    private var list: RecyclerView? = null
    private var activitylistaproduto: RelativeLayout? = null
    lateinit var adapter: RecycleFilmeListaAdapter
    internal var tipoLista = ""
    //    List<Filme> filmes = new ArrayList<>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_produto)
        this.activitylistaproduto = findViewById(R.id.activity_lista_produto) as RelativeLayout
        this.list = findViewById(R.id.list) as RecyclerView
        this.appBarLayout = findViewById(R.id.appBarLayout) as AppBarLayout
        this.toolbar = findViewById(R.id.toolbar) as Toolbar


        list!!.setHasFixedSize(true)
        adapter = RecycleFilmeListaAdapter(this)
        val llm = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        llm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (adapter.getItemViewType(position)) {
                    2 -> return 3
                    1 -> return 1
                    else -> return -1
                }
            }
        }
        list!!.adapter = adapter
        list!!.layoutManager = llm

        tipoLista = intent.getStringExtra("lista")
        if (toolbar != null) {
            when (tipoLista) {
                "todos" -> {
                    toolbar!!.title = "Os mais populares"
                    list!!.addOnScrollListener(object : EndlessRecyclerViewScrollListener(llm) {
                        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                            adapter.isLoading = true
                            adapter.notifyItemChanged(adapter.itemCount)
                            getpopulares(page)
                        }
                    })
                    adapter.replace(getpopulares(1))
                }

                "favoritos" -> {
                    tipoLista = "favoritos"
                    getfavoritos()
                    toolbar!!.title = "Meus favoritos"
                }

                "curtidos" -> {
                    tipoLista = "like"
                    list!!.addOnScrollListener(object : EndlessRecyclerViewScrollListener(llm) {
                        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                            adapter.isLoading = true
                            adapter.notifyItemChanged(adapter.itemCount)
                            getcurtidos(page)
                        }
                    })
                    adapter.replace(getcurtidos(1))
                    toolbar!!.title = "Os mais curtidos"
                }
            }
            toolbar!!.setTitleTextColor(Color.parseColor("#2d2d2d"))
            toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
            setSupportActionBar(toolbar)
        }

        adapter.recyclerViewOnClickListenerHack = object : RecyclerViewOnClickListenerHack {
            override fun onClickListener(view: View, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onClickListener(view: View, `object`: Any, position: Int) {
                val filme = `object` as Filme
                startActivity(Intent(baseContext, DetalhesProdutosActivity::class.java)
                        .putExtra("filme", filme))
            }

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun getpopulares(page: Int): List<Filme> {

        val filmes = ArrayList<Filme>()

        ApiManager(baseContext,"http://api.themoviedb.org/3/movie/")
                .retrofit
                .create(RequestInterface::class.java)
                .getFilmesPopular(br.com.luan.myskeletonv2.view.ui.FindMovie.MainActivity.api_key, page.toString())
                .enqueue(CustomCallback(this, object : CustomCallback.OnResponse<MainRequest> {
                    override fun onResponse(response: MainRequest?) {
                        Log.d("", "onResponse: ")
                        filmes.addAll(response!!.results!!)
                        adapter.add(filmes)
                        adapter.isLoading = false

                    }

                    override fun onFailure(t: Throwable?) {
                        Toast.makeText(baseContext, t!!.message, Toast.LENGTH_SHORT).show()
                        adapter.isLoading = false

                    }

                    override fun onRetry(t: Throwable?) {
                        getpopulares(page)
                    }
                }, false))

        return filmes
    }


    fun getcurtidos(page: Int): List<Filme> {

        val filmes = ArrayList<Filme>()

        ApiManager(baseContext,"http://api.themoviedb.org/3/movie/")
                .retrofit
                .create(RequestInterface::class.java)
                .getFilmesVotados(br.com.luan.myskeletonv2.view.ui.FindMovie.MainActivity.api_key, page.toString())
                .enqueue(CustomCallback(this, object : CustomCallback.OnResponse<MainRequest> {
                    override fun onResponse(response: MainRequest?) {
                        Log.d("", "onResponse: ")
                        filmes.addAll(response!!.results!!)
                        adapter.add(filmes)
                        adapter.isLoading = false

                    }

                    override fun onFailure(t: Throwable?) {
                        Toast.makeText(baseContext, t!!.message, Toast.LENGTH_SHORT).show()
                        adapter.isLoading = false

                    }

                    override fun onRetry(t: Throwable?) {
                        getpopulares(page)
                    }
                }, false))

        return filmes
    }

    fun getfavoritos() {
        val realm = Realm.getDefaultInstance()

        var favoritos: List<Filme> = ArrayList()
        favoritos = realm.where(Filme::class.java).findAll()

        adapter.replaceFavoritos(favoritos)


    }

    override fun onResume() {
        if (tipoLista.equals("favoritos", ignoreCase = true)) {
            getfavoritos()
        }
        super.onResume()
    }

    override fun onRestart() {
        if (tipoLista.equals("favoritos", ignoreCase = true)) {
            getfavoritos()
        }
        super.onRestart()
    }
}
