package br.com.luan.myskeletonv2.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.RecyclerViewOnClickListenerHack
import com.facebook.shimmer.ShimmerFrameLayout
import java.text.Normalizer
import java.util.ArrayList

class RecycleShimmerAdapter(private val context: Context, private val layout:Int) : RecyclerView.Adapter<RecycleShimmerAdapter.ViewHolder>(){
    private val inflater: LayoutInflater
    private val itens = ArrayList<String>()
    private val originalList = ArrayList<String>()
    var recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack? = null

    var isLoading = false

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getItemViewType(position: Int): Int {
       return 0
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

            val v = inflater.inflate(layout, viewGroup, false)
            return ViewHolder(v)


    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.content!!.startShimmerAnimation()

    }



    override fun getItemCount(): Int {
        return 10
    }

    fun addRecyclerViewOnClickListenerHack(recyclerViewOnClickListenerHack: RecyclerViewOnClickListenerHack) {
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        internal var content: ShimmerFrameLayout?


        init {
//            content = itemView.findViewById(R.id.shimmer_content) as ShimmerFrameLayout?

        }
    }




}


