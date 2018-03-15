package br.com.luan.myskeletonv2.view.ui.Realm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.squareup.picasso.Picasso

import java.util.ArrayList

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.R.id.itens
import br.com.luan.myskeletonv2.view.ui.Realm.model.Constants
import br.com.luan.myskeletonv2.view.ui.Realm.model.Contact
import br.com.luan.myskeletonv2.view.ui.Realm.model.Conversa
import de.hdodenhof.circleimageview.CircleImageView
import io.realm.RealmList

/**
 * Created by Luan on 15/03/18.
 */
class ChatRecycleAdapter(private val context: Context, internal var contact: Contact) : RecyclerView.Adapter<ChatRecycleAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private var itens = RealmList<Conversa>()

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        itens = contact.conversation
    }

    override fun getItemViewType(position: Int): Int {
        //if(usuario.getId().equalsIgnoreCase(itens.get(position).getReceiver())){
        return if (Constants.idUser.equals(itens[position].idOwner)) {
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            val v = inflater.inflate(R.layout.adapter_message_right, viewGroup, false)
            return ChatRecycleAdapter.ViewHolder(v)
        } else if (viewType == 1) {
            val v = inflater.inflate(R.layout.adapter_mensagem, viewGroup, false)
            return ChatRecycleAdapter.ViewHolder(v)

        } else {
            val v = inflater.inflate(R.layout.adapter_message_data, viewGroup, false)
            return ChatRecycleAdapter.ViewHolder(v)
        }
    }

    override
    fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = itens[position]
        if (getItemViewType(position) == 3) {
            //            try {
            //                viewHolder.horaTextView.setText(item.getData());
            //            }catch (Exception ex){
            //                viewHolder.horaTextView.setText("");
            //            }
        } else {
            prepare(viewHolder, item, position)
        }


    }

    private fun prepare(viewHolder: ViewHolder, item: Conversa, position: Int) {

        viewHolder.text!!.text = item.message
    }

    override fun getItemCount(): Int {
        return itens.size
    }


    fun replaceItens(chat: Contact) {
        this.itens.clear()
        this.itens.addAll(chat.conversation)
        notifyDataSetChanged()
    }

    fun addItem(`object`: Conversa) {
        this.itens.add(`object`)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Conversa? {
        try {
            return this.itens[position]
        } catch (ex: Exception) {
            return null
        }

    }


    public class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var profileImage: CircleImageView?
        internal var text: TextView?
        internal var horaTextView: TextView?
//        internal var nomeusuario: TextView?
        internal var hora: TextView?

        init {
            profileImage = view.findViewById(R.id.profile_image) as CircleImageView?
            text = view.findViewById(R.id.text) as TextView?
            horaTextView = view.findViewById(R.id.horaTextView) as TextView?
            hora = view.findViewById(R.id.hora) as TextView?
            // nomeusuario = (TextView) view.findViewById(R.id.nomeusuario);
        }
    }
}
