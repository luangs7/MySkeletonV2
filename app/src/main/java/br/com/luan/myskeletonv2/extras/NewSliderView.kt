package br.com.luan.myskeletonv2.extras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import br.com.luan.myskeletonv2.R

import com.daimajia.slider.library.SliderTypes.DefaultSliderView

/**
 * Created by luan on 08/04/2017.
 */

//Aqui eu criei uma classe que herda da classe criada pelo componente, assim posso customizar o scaletype da imagem

class NewSliderView(context: Context) : DefaultSliderView(context) {

    override fun getView(): View {
        val v = LayoutInflater.from(context).inflate(R.layout.render_type_default, null)
        val target = v.findViewById(R.id.daimajia_slider_image) as ImageView
        target.scaleType = ImageView.ScaleType.CENTER
        bindEventAndShow(v, target)
        return super.getView()
    }
}
