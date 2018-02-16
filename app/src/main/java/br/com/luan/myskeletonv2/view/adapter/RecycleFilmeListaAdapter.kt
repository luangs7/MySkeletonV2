package br.com.luan.myskeletonv2.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

import java.util.ArrayList

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.RecyclerViewOnClickListenerHack
import br.com.luan.myskeletonv2.view.ui.FindMovie.Filme


class RecycleFilmeListaAdapter(private val context: Context) : RecyclerView.Adapter<RecycleFilmeListaAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val itens = ArrayList<Filme>()
    var recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack? = null

    var isLoading = false

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itens.size) {
            2
        } else {
            1
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 2) {
            val v = inflater.inflate(R.layout.item_loader_lista, viewGroup, false)
            return ViewHolder(v)
        } else {
            val v = inflater.inflate(R.layout.item_produto_lista, viewGroup, false)
            return ViewHolder(v)
        }

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (getItemViewType(position) == 2) {
            prepareLoad(viewHolder)
        } else {
            val selector = itens[position]
            prepare(viewHolder, selector, position)
        }
    }

    fun prepareLoad(viewHolder: ViewHolder) {
        if (isLoading)
            viewHolder.loading!!.visibility = View.VISIBLE
        else
            viewHolder.loading!!.visibility = View.GONE

    }

    private fun prepare(viewHolder: ViewHolder, selector: Filme, position: Int) {

        viewHolder.nome!!.text = selector.title
        viewHolder.categoria!!.text = selector.originalTitle
        val path = "http://image.tmdb.org/t/p/w185/" + selector.posterPath

        try {
            Picasso.with(context).load(path)
                    .fit().centerCrop()
                    .into(viewHolder.foto, object : Callback {
                        override fun onSuccess() {
                            Log.d("okpicasso", "ok")
                        }

                        override fun onError() {
                            Log.d("erropicasso", "erro")
                        }
                    })

        } catch (e: Exception) {
            Log.d("erropicasso", e.message)

        }

        viewHolder.itemView.setOnClickListener { view ->
            if (recyclerViewOnClickListenerHack != null)
                recyclerViewOnClickListenerHack!!.onClickListener(view, selector, itens.indexOf(selector))
        }
    }

    override fun getItemCount(): Int {
        return itens.size + 1
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nome: TextView?
        internal var categoria: TextView?
        internal var foto: ImageView?
        internal var loading: ProgressBar?

        init {
            nome = itemView.findViewById(R.id.nome) as TextView?
            categoria = itemView.findViewById(R.id.categoria) as TextView?
            loading = itemView.findViewById(R.id.loading) as ProgressBar?
            foto = itemView.findViewById(R.id.foto) as ImageView?
        }
    }

    fun replace(itens: List<Filme>) {
        this.itens.clear()
        this.itens.addAll(itens)
        //notifyDataSetChanged();
    }

    fun replaceFavoritos(itens: List<Filme>) {
        this.itens.clear()
        this.itens.addAll(itens)
        notifyDataSetChanged()
    }

    fun clear() {
        this.itens.clear()
        //notifyDataSetChanged();
    }

    fun add(itens: List<Filme>) {
        val pos = itens.size
        this.itens.addAll(itens)
        //        notifyItemRangeInserted(pos,itens.size()+1);
        notifyDataSetChanged()
    }
}
