package br.com.luan.myskeletonv2.view.ui.Expandable

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import com.github.aakira.expandablelayout.ExpandableLayout
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter
import kotlinx.android.synthetic.main.activity_swipe.*

class ExpandableActivity : BaseDrawerActivity() {

    var expandedState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)


        setSupportActionBar(toolbar)
        expandableLayout.setInRecyclerView(true)
        expandableLayout.setBackgroundColor(Color.WHITE)
        expandableLayout.setExpanded(expandedState)
        expandableLayout.setListener(object : ExpandableLayoutListenerAdapter() {
            override fun onPreOpen() {
                expandedState = true
                arrow.setImageResource(R.drawable.ic_up_arrow)
            }

            override fun onPreClose() {
                expandedState = false
                arrow.setImageResource(R.drawable.ic_arrow_down)
            }
        })

//        holder.content.setRotation(expandState.get(position) ? 180f : 0f);
        content.setOnClickListener(View.OnClickListener {  onClickButton(expandableLayout)})
    }

    private fun onClickButton(expandableLayout: ExpandableLayout) {
        expandableLayout.toggle()
    }


    // ------------------ below a example using recyclerview in adapter ------------------------


//    private val expandState = SparseBooleanArray()
//    this.data = data
//    if (data != null)
//    {
//        for (i in data.indices) {
//            expandState.append(i, false)
//        }
//    }
//    holder.quantidade.setRating(item.getQuality())
//    holder.negociacao.setRating(item.getNegociation())
//    holder.setIsRecyclable(false)
//    holder.itemView.setBackgroundColor(Color.WHITE)
//    holder.expandableLayout.setInRecyclerView(true)
//    holder.expandableLayout.setBackgroundColor(Color.WHITE)
//    holder.expandableLayout.setExpanded(expandState.get(position))
//    holder.expandableLayout.setListener(
//    object : ExpandableLayoutListenerAdapter() {
//        override fun onPreOpen() {
//            expandState.put(position, true)
//            holder.arrow.setImageResource(R.drawable.ic_up_arrow)
//        }
//
//        override fun onPreClose() {
//            expandState.put(position, false)
//            holder.arrow.setImageResource(R.drawable.ic_arrow_down)
//        }
//    })
//
////        holder.content.setRotation(expandState.get(position) ? 180f : 0f);
//    holder.content.setOnClickListener(
//    object : View.OnClickListener {
//        override fun onClick(v: View) {
//            onClickButton(holder.expandableLayout)
//        }
//    })

}
