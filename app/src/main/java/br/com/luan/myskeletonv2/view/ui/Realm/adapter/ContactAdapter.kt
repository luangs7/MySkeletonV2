package br.com.luan.myskeletonv2.view.ui.Realm.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.widget.ImageView
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.view.ui.Realm.model.Contact


/**
 * Created by Luan on 15/03/18.
 */
class ContactAdapter(context: Context, objects: ArrayList<Contact>):BaseAdapter() {
    private var objects: ArrayList<Contact> = ArrayList()
    private var context: Context? = null
    var tipo: Int = 0

    init {
        this.objects = objects
        this.context = context
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var v: View?
        val vh: ViewHolder


        if (convertView == null) {
            vh = ViewHolder()
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = inflater.inflate(R.layout.adapter_contact, null)

            vh.nome = v!!.findViewById(R.id.name) as TextView
            vh.description = v.findViewById(R.id.description) as TextView

            v.tag = vh
        } else {
            v = convertView
            vh = convertView.tag as ViewHolder
        }


        val model = objects[position]
        vh.nome!!.text = model.name
        vh.description!!.text = model.description

        return v

    }

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(position: Int): Contact {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private inner class ViewHolder {
        var nome: TextView? = null
        var description: TextView? = null
    }

}