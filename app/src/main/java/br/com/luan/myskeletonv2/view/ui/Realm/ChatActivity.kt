package br.com.luan.myskeletonv2.view.ui.Realm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.luan.myskeletonv2.data.realm.RealmHelper
import br.com.luan.myskeletonv2.view.ui.Realm.adapter.ChatRecycleAdapter
import br.com.luan.myskeletonv2.view.ui.Realm.model.Constants
import br.com.luan.myskeletonv2.view.ui.Realm.model.Contact
import br.com.luan.myskeletonv2.view.ui.Realm.model.Conversa
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*


class ChatActivity : BaseDrawerActivity() {
    lateinit var adapter:ChatRecycleAdapter
    lateinit var realmContact:Contact
    lateinit var contact:Contact
    lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        setSupportActionBar(this.toolbar)

        id = intent.getStringExtra("idContact")
        realmContact = RealmHelper(this).queryById(id.toInt())


        if(::realmContact.isInitialized){
            contact = realmContact
            initView()
        }else{
            onErrorAlert("Houve um erro ao pesquisar suas conversas.")
        }

    }

    fun initView(){
        adapter = ChatRecycleAdapter(this, contact)
        recycleview.setHasFixedSize(true)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycleview.setLayoutManager(llm)
        recycleview.setAdapter(adapter)


        this.buttonsend.setOnClickListener(View.OnClickListener { v: View? ->
            submitMessage()
            this.mensagem.setText("")
        })
    }

    fun submitMessage(){

        var rand= Random()
        val conversa = Conversa(this.mensagem.text.toString(),"",Constants.idUser,rand.nextInt(999))

        contact.conversation.add(conversa)
        adapter.notifyDataSetChanged()
        RealmHelper(this).addElement(contact)

        this.recycleview.scrollToPosition(adapter.itemCount -1 )
    }
}
