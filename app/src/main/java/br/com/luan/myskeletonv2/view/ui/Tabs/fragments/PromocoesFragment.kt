package br.com.squarebits.lausgirl.ui.view.fragments


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.luan.myskeletonv2.R
import kotlinx.android.synthetic.main.fragment_base.*


/**
 * A simple [Fragment] subclass.
 */
class PromocoesFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_base, null)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.tab.text = "TAB 3"

    }


}
