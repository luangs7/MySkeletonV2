package br.com.luan.myskeletonv2.view.ui.ShimmerList.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.RecyclerViewOnClickListenerHack
import br.com.luan.myskeletonv2.view.ui.ShimmerList.model.Order

import java.text.Normalizer
import java.util.ArrayList

class RecycleProdutoPedidoAdapter(private val context: Context) : RecyclerView.Adapter<RecycleProdutoPedidoAdapter.ViewHolder>(), Filterable{
    private val inflater: LayoutInflater
    private val itens = ArrayList<Order>()
    private val originalList = ArrayList<Order>()
    var recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack? = null

    var isLoading = false

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == originalList.size) {
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
            val v = inflater.inflate(R.layout.adapter_pedidos, viewGroup, false)
            return ViewHolder(v)
        }

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (getItemViewType(position) == 2) {
            prepareLoad(viewHolder)
        } else {
            val selector = originalList[position]
            prepare(viewHolder, selector, position)
        }
    }

    fun prepareLoad(viewHolder: ViewHolder) {
        if (isLoading)
            viewHolder.loading?.visibility = View.VISIBLE
        else
            viewHolder.loading?.visibility = View.GONE

    }

    private fun prepare(viewHolder: ViewHolder, selector: Order, position: Int) {

        viewHolder.numeroPedido?.text = "Pedido Nº "+ selector.orderNumber
        viewHolder.status?.text = selector.delivery!!.toUpperCase()
        viewHolder.date?.text = "Pedido efetuado em: " + selector.createdAt
        viewHolder.value?.text = selector.priceTotal
        viewHolder.statusPedido?.text = selector.status


        viewHolder.itemView.setOnClickListener { view ->
            if (recyclerViewOnClickListenerHack != null)
                recyclerViewOnClickListenerHack!!.onClickListener(view, selector, position)
        }
    }

    override fun getItemCount(): Int {
        return originalList.size
    }

    fun addRecyclerViewOnClickListenerHack(recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack) {
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var numeroPedido: TextView?
        internal var description: TextView?
        internal var status: TextView?
        internal var statusPedido: TextView?
        internal var date: TextView?
        internal var value: TextView?
        internal var loading: ProgressBar?

        init {
            numeroPedido = itemView.findViewById(R.id.numeroPedido) as TextView?
            description = itemView.findViewById(R.id.descriptionPedido) as TextView?
            statusPedido = itemView.findViewById(R.id.status) as TextView?
            status = itemView.findViewById(R.id.statusPedido) as TextView?
            date = itemView.findViewById(R.id.datePedido) as TextView?
            value = itemView.findViewById(R.id.valuePedido) as TextView?
            loading = itemView.findViewById(R.id.loading) as ProgressBar?

        }
    }

    fun replace(itens: List<Order>) {
        this.originalList.clear()
        this.originalList.addAll(itens)
        this.itens.addAll(itens)
        notifyDataSetChanged();
    }

    fun refreshSearch(){
        this.originalList.clear()
        this.originalList.addAll(this.itens)
        notifyDataSetChanged()
    }

    fun clear() {
        this.originalList.clear()
        //notifyDataSetChanged();
    }

    fun add(itens: List<Order>) {
//        val pos = originalList.size
        this.originalList.addAll(itens)
        //        notifyItemRangeInserted(pos,itens.size()+1);
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        var user = UserFilter(this,originalList)
        return user
    }


     class UserFilter  constructor(private val adapter: RecycleProdutoPedidoAdapter, private val originalList: List<Order>) : Filter() {

        private val newList: ArrayList<Order>

        init {
            this.newList = ArrayList<Order>()
        }

        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            newList.clear()
            val results = Filter.FilterResults()

            if (constraint.length == 0) {
                newList.addAll(adapter.itens)
            } else {
                val a = Normalizer.normalize(constraint.toString().toLowerCase().trim { it <= ' ' }, Normalizer.Form.NFD)
                val filterPattern = a.replace("[^\\p{ASCII}]".toRegex(), "")

                for (`object` in adapter.itens) {
                    var text = ""
                    text = Normalizer.normalize(`object`.orderNumber!!.toLowerCase().trim(), Normalizer.Form.NFD)


                    val x = text.replace("[^\\p{ASCII}]".toRegex(), "")
                    if (x.contains(filterPattern)) {
                        newList.add(`object`)
                    }
                }
            }
            results.values = newList
            results.count = newList.size
            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {

            adapter.originalList.clear()
            adapter.originalList.addAll(results.values as ArrayList<Order>)
            if (adapter.originalList.size > 0)
                adapter.notifyDataSetChanged()
            else {
//                adapter.itens.addAll(adapter.originalList)
                adapter.notifyDataSetChanged()

            }

        }

    }
}


