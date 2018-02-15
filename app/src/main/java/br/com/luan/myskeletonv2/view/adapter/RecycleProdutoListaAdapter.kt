package br.com.luan.myskeletonv2.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.data.model.Product
import br.com.luan.myskeletonv2.extras.RecyclerViewOnClickListenerHack
import br.com.luan.myskeletonv2.view.ui.Products.ShowImageActivity


import com.squareup.picasso.Picasso


import me.relex.circleindicator.CircleIndicator
import java.util.*
import kotlin.collections.ArrayList


class RecycleProdutoListaAdapter(private val context: Activity) : RecyclerView.Adapter<RecycleProdutoListaAdapter.ViewHolder>(), SliderMainAdapter.OnSliderClickListener {
    private val inflater: LayoutInflater
    private val itens = ArrayList<Product>()
    var recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack? = null
    lateinit var adapter: RecycleProdutoListaAdapter
    var isLoading = false
    var banners = ArrayList<String>()



    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }



    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
             }
        if(position == itens.size && position != 0){
            return 2
        }else
            return 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            val v = inflater.inflate(R.layout.header_recycle, viewGroup, false)
            return ViewHolder(v)
        }
        else if (viewType == 2) {
            val v = inflater.inflate(R.layout.item_loader_lista, viewGroup, false)
            return ViewHolder(v)
        } else {
            val v = inflater.inflate(R.layout.item_produto, viewGroup, false)
            return ViewHolder(v)
        }

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {

            banners.add("http://www.primeironegocio.com/wp-content/uploads/2016/07/roupas-femininas-fitness-consignado.jpg")
            banners.add("https://fthmb.tqn.com/8J-gnDSb5unt_UP0t05sNJldLJc=/1500x1000/filters:fill(auto,1)/PHP-code-58d2d5803df78c51623a6ce2.jpg")
            banners.add("https://www.a2hosting.com/blog/content/uploads/2017/04/aspnet-php-660x330.png")
            prepareHeader(viewHolder,position,banners)
        }
        else if (getItemViewType(position) == 2) {
            prepareLoad(viewHolder)
        } else {
            val selector = itens[position]
            prepare(viewHolder, selector, position)
        }
    }

    fun prepareLoad(viewHolder: ViewHolder) {
        if (isLoading)
            viewHolder.loading?.visibility = View.VISIBLE
        else
            viewHolder.loading?.visibility = View.GONE

    }

    private fun prepareHeader(viewHolder: ViewHolder, position: Int, banners:List<String>){
        if(!banners.isEmpty()) {
            var sliderAdapter = SliderMainAdapter(context, banners)
            sliderAdapter.setSliderClickListener(this)

            viewHolder.viewPagerSlider?.adapter = sliderAdapter
            setTimerSlider(viewHolder, sliderAdapter.count)
            viewHolder.sliderLayout?.visibility = View.VISIBLE
        }else{
            viewHolder.sliderLayout?.visibility = View.GONE
        }

        viewHolder.indicator?.setViewPager(viewHolder.viewPagerSlider)



    }

    private fun prepare(viewHolder: ViewHolder, selector: Product, position: Int) {

        viewHolder.nome?.text = selector.name
        viewHolder.categoria?.text = selector.category

        if (selector.isIsNew)
            viewHolder.lancamento?.visibility = View.VISIBLE


            Picasso.with(context)
                    .load(selector.image)
                    .fit()
                    .centerCrop()
                    .into(viewHolder.foto)



        viewHolder.itemView.setOnClickListener { view ->
            if (recyclerViewOnClickListenerHack != null)
                recyclerViewOnClickListenerHack!!.onClickListener(view, selector, position)
        }
    }

    override fun getItemCount(): Int {
        return itens.size + 1
    }

    fun addRecyclerViewOnClickListenerHack(recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack) {
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nome: TextView?
        internal var categoria: TextView?
        internal var lancamento: RelativeLayout?
        internal var sliderLayout: RelativeLayout?
        internal var divider: LinearLayout?
        internal var foto: ImageView?
        internal var loading: ProgressBar?
        internal var viewPagerSlider: ViewPager?
        internal var indicator: CircleIndicator?

        init {
            nome = itemView.findViewById(R.id.nomeProduto) as TextView?
            categoria = itemView.findViewById(R.id.categoriaProduto) as TextView?
            loading = itemView.findViewById(R.id.loading) as ProgressBar?
            lancamento = itemView.findViewById(R.id.lancamentoLayout) as RelativeLayout?
            sliderLayout = itemView.findViewById(R.id.relative) as RelativeLayout?
            divider = itemView.findViewById(R.id.divider) as LinearLayout?
            foto = itemView.findViewById(R.id.foto) as ImageView?
            viewPagerSlider = itemView.findViewById(R.id.viewPagerSlider) as ViewPager?
            indicator = itemView.findViewById(R.id.indicator) as CircleIndicator?

        }
    }

    fun replace(itens: List<Product>) {
        this.itens.clear()
        this.itens.add(Product())
        this.itens.addAll(itens)
        notifyDataSetChanged();
    }

    fun clear() {
        this.itens.clear()
        //notifyDataSetChanged();
    }

    fun add(itens: List<Product>) {
        this.itens.addAll(itens)
        //        notifyItemRangeInserted(pos,itens.size()+1);
        notifyDataSetChanged()
    }


    fun getItemAtPosition(position: Int): Product {
        return this.itens.get(position)
    }

    lateinit var timer: Timer;
    var handler: Handler = Handler();

    fun setTimerSlider(viewHolder: ViewHolder, sliderAdapterCount: Int){
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (viewHolder.viewPagerSlider != null) {
                        if (viewHolder.viewPagerSlider!!.getCurrentItem() + 1 == sliderAdapterCount) {
                            viewHolder.viewPagerSlider!!.setCurrentItem(0, true)
                        } else {
                            viewHolder.viewPagerSlider!!.setCurrentItem(viewHolder.viewPagerSlider!!.getCurrentItem() + 1, true)
                        }
                    }
                }
            }
        }, 10000, 10000)
    }

    fun cancelTimerSlider(){
        timer.purge()
        timer.cancel()
    }

    override fun onClick(v: View, position: Int) {
        transition(v,position)
    }


    private fun transition(view: View,position: Int) {
        if (Build.VERSION.SDK_INT < 21) {
            Toast.makeText(context, "21+ only, keep out", Toast.LENGTH_SHORT).show()
        } else {
            // note: This block is only for prototype!
                getItemAtPosition(position).images = banners
            // ----------------------------
            val intent = Intent(context, ShowImageActivity::class.java).putExtra("product",getItemAtPosition(position))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, "test")
            context.startActivity(intent, options.toBundle())
        }
    }

}
