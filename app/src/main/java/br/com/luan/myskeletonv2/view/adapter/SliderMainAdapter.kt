package br.com.luan.myskeletonv2.view.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import br.com.luan.myskeletonv2.R

import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso



class SliderMainAdapter(var context: Context, var images: List<String>?) : PagerAdapter() {
    var inflater: LayoutInflater
    var onSliderClickListener: OnSliderClickListener? = null


    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun setSliderClickListener(onSliderClickListener: OnSliderClickListener){
        this.onSliderClickListener = onSliderClickListener
    }

    override fun getCount(): Int {
        return if (images == null || images!!.size == 0)
            0
        else
            images!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val pager = container as ViewPager
        val view = LayoutInflater.from(context).inflate(R.layout.slider_image_adapter, null)
        val progressBar = view.findViewById(R.id.progressBar2) as ProgressBar
        val thumb = view.findViewById(R.id.imageView6) as ImageView

        view.setOnClickListener {
            if (onSliderClickListener != null) {
                onSliderClickListener!!.onClick(thumb, position)
            }
        }

        //final ImageView thumb = new ImageView(context);
        pager.addView(view)

        images.let {

            Picasso.with(context).load(images!![position])
                    .fit().centerCrop()
                    //.placeholder(R.drawable.shop)
//                    .error(R.drawable.placeholder)
                    .into(thumb, object : Callback {
                        override fun onSuccess() {
                            progressBar.isIndeterminate = false
                            progressBar.visibility = View.GONE
                        }

                        override fun onError() {
                            progressBar.isIndeterminate = false
                            progressBar.visibility = View.GONE
                        }
                    })

        }
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`//((View)object);
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        try {
            // ((ViewPager) container).removeView((ImageView) object);
            (container as ViewPager).removeView(`object` as View)
        } catch (ex: Exception) {
            Log.e("Slider", "destroyItem: ", ex)
        }

    }

    interface OnSliderClickListener {
        fun onClick(v: View, position: Int)
    }
}
