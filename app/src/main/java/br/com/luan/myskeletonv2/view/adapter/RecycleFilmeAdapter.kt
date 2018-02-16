package br.com.luan.myskeletonv2.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
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


class RecycleFilmeAdapter(private val context: Context) : RecyclerView.Adapter<RecycleFilmeAdapter.ViewHolder>() {
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
            val v = inflater.inflate(R.layout.item_loader, viewGroup, false)
            return ViewHolder(v)
        } else {
            val v = inflater.inflate(R.layout.item_produto, viewGroup, false)
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
        viewHolder.nome!!.text = selector.originalTitle
        viewHolder.categoria!!.text = selector.title


        try {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + selector.posterPath)
                    .fit().centerCrop()
                    .into(viewHolder.foto, object : Callback {
                        override fun onSuccess() {}

                        override fun onError() {}
                    })

        } catch (e: Exception) {

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
        internal var rating: TextView? = null
        internal var categoria: TextView?
        internal var foto: ImageView?
        internal var loading: ProgressBar?

        init {
            nome = itemView.findViewById(R.id.nomeProduto) as TextView?
            categoria = itemView.findViewById(R.id.categoriaProduto) as TextView?
            loading = itemView.findViewById(R.id.loading) as ProgressBar?
            foto = itemView.findViewById(R.id.foto) as ImageView?
        }
    }

    fun replace(itens: List<Filme>?) {
        itens?.let {
            this.itens.clear()
            this.itens.addAll(itens)
            notifyDataSetChanged()
        }
    }

    fun add(itens: List<Filme>) {
        val pos = itens.size
        this.itens.addAll(itens)
        //        notifyItemRangeInserted(pos,itens.size()+1);
        notifyDataSetChanged()
    }
}
