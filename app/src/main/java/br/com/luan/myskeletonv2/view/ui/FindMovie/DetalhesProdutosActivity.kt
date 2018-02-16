package br.com.luan.myskeletonv2.view.ui.FindMovie

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.daimajia.slider.library.Indicators.PagerIndicator
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.NewSliderView
import br.com.luan.myskeletonv2.extras.ProgressTask
import br.com.luan.myskeletonv2.extras.Utils
import br.com.luan.myskeletonv2.view.ui.BaseActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_detalhes_produtos.*

class DetalhesProdutosActivity : BaseActivity() {


    lateinit var filmeSelect: Filme
    lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_produtos)


        if (toolbar != null) {
            toolbar!!.title = ""
            toolbar!!.setTitleTextColor(Color.parseColor("#2d2d2d"))
            toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
            setSupportActionBar(toolbar)
        }


        slider!!.stopAutoCycle()
        slider!!.setPresetTransformer("Default")
        filmeSelect = intent.getParcelableExtra("filme")

        sliderImage("http://image.tmdb.org/t/p/w185/" + filmeSelect.posterPath!!)

        //inicio uma instancia do Realm
        realm = Realm.getDefaultInstance()


        //<------------ Listeners ------------------>

        this.custom_indicator!!.setOnClickListener { v -> Log.i("LOG", "ID: " + v.id) }


        //chama a minha classe de progress
        btnTrailer!!.setOnClickListener {
            val progressTask = ProgressTask(this@DetalhesProdutosActivity, filmeSelect)
            progressTask.execute()
        }



        btnTrailer!!.setOnClickListener {
            val progressTask = ProgressTask(this@DetalhesProdutosActivity, filmeSelect)
            progressTask.execute()
        }

        //vejo se esse filme já está favoritado ou não!

        val result = realm.where(Filme::class.java).equalTo("id", filmeSelect.id).findAll()

        if (result.size > 0) {
            // Exists
            btnDesfav!!.visibility = View.VISIBLE
            btnFav!!.visibility = View.GONE
        } else {
            // Not exist
            btnDesfav!!.visibility = View.GONE
            btnFav!!.visibility = View.VISIBLE

        }

        //trabalho com o Realm para salvar ou retirar o filme da base

        btnDesfav!!.setOnClickListener {
            try {
                realm.beginTransaction()
                result.deleteAllFromRealm()
                realm.commitTransaction()
                btnDesfav!!.visibility = View.GONE
                btnFav!!.visibility = View.VISIBLE
                Toast.makeText(this@DetalhesProdutosActivity, "Item foi tirado de sua lista de favoritos!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(this@DetalhesProdutosActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }


        btnFav!!.setOnClickListener {
            try {
                realm.beginTransaction()
                realm.copyToRealm(filmeSelect)
                realm.commitTransaction()
                btnDesfav!!.visibility = View.VISIBLE
                btnFav!!.visibility = View.GONE
                Toast.makeText(this@DetalhesProdutosActivity, "Favoritado com sucesso!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@DetalhesProdutosActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        //        chama o metodo que seta os dados na tela
        setData(filmeSelect)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        return true
    }

    fun sliderImage(path: String) {
        //            Como é apenas um imagem, seto ela para o componente
        //        caso fosse mais de uma, haveria aqui um for

        val aux = NewSliderView(this@DetalhesProdutosActivity)
        aux.image(path)
        aux.scaleType = BaseSliderView.ScaleType.CenterInside
        aux.setOnSliderClickListener { slider!!.stopAutoCycle() }
        slider!!.addSlider(aux)


    }


    fun setData(filme: Filme) {
        nome!!.text = filme.title
        categoria!!.text = filme.originalTitle
        rating!!.text = filme.voteAverage
        linguagem!!.text = filme.originalLanguage
        descricao!!.text = filme.overview

    }
}
