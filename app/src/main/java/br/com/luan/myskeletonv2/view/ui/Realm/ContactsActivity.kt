package br.com.luan.myskeletonv2.view.ui.Realm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.R.id.download
import br.com.luan.myskeletonv2.data.realm.RealmHelper
import br.com.luan.myskeletonv2.view.ui.BaseDrawerActivity
import br.com.luan.myskeletonv2.view.ui.Realm.adapter.ContactAdapter
import br.com.luan.myskeletonv2.view.ui.Realm.model.Contact
import br.com.luan.myskeletonv2.view.ui.Realm.model.Conversa
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_contacts.*
import org.jetbrains.anko.longToast
import java.util.*
import kotlin.collections.ArrayList

class ContactsActivity : BaseDrawerActivity() {
    var contacts = ArrayList<Contact>()
    lateinit var adapter:ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        setSupportActionBar(this.toolbar)

        contacts = RealmHelper(this).queryAll().toCollection(ArrayList())


        if(contacts.size == 0)
            genetareContact()
        else
            initView()



    }

    fun initView(){
        adapter = ContactAdapter(this,contacts)
        this.contactsList.adapter = this.adapter

        this.contactsList.setOnItemClickListener { parent, view, position, id ->
            val model = parent.getItemAtPosition(position) as Contact
            val intent = Intent(baseContext, ChatActivity::class.java)
            intent.putExtra("idContact",model.id.toString())
            startActivity(intent)
        }

    }

    fun genetareContact(){
        var rand= Random()
        var id = 0
        for (i in 1..10){
            id = rand.nextInt(998)
            contacts.add(Contact(id,"","Fulano" + i,"Gestor de Vendas",genetareChat(id)))
        }

        RealmHelper(this).addElement(contacts)
        initView()
    }

    fun genetareChat(idOwner:Int): RealmList<Conversa> {
        var rand= Random()
        var conversa = RealmList<Conversa>()
        conversa.add(Conversa("Loren ipsun","",idOwner,rand.nextInt(998)))
        conversa.add(Conversa("Loren ipsun","",idOwner,rand.nextInt(998)))
        conversa.add(Conversa("Loren ipsun","",idOwner,rand.nextInt(998)))
        conversa.add(Conversa("Loren ipsun","",idOwner,rand.nextInt(998)))
        conversa.add(Conversa("Loren ipsun","",idOwner,rand.nextInt(998)))
        return conversa
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menu.clear()
//        menuInflater.inflate(R.menu.menu_chat, menu)
//
//        return true
//
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val id = when(item.itemId){
            android.R.id.home ->  onBackPressed()
            R.id.reload -> onReload()
            else -> return true

        }

        return super.onOptionsItemSelected(item)

    }

    fun onReload(){
        RealmHelper(this).deleteAllFromRealm()
        genetareContact()
    }
}
