package br.com.luan.myskeletonv2.data.realm

import android.content.Context

import br.com.luan.myskeletonv2.view.ui.Realm.model.Contact
import br.com.luan.myskeletonv2.view.ui.Realm.model.Conversa

import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by Luan on 02/08/17.
 */

class RealmHelper(context: Context) {
    /**
     * query
     */


    //    public boolean isExists(String id){
    //        Search mSearch=mRealm.where(Search.class).equalTo("id",id).findFirst();
    //        if (mSearch==null){
    //            return false;
    //        }else {
    //            return  true;
    //        }
    //    }

    var realm: Realm? = null
        private set

    init {

        realm = Realm.getDefaultInstance()
    }


    /**
     * add （增）
     */
    fun addElement(contact: Contact) {
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance()
            realm!!.executeTransaction { realm -> realm.insertOrUpdate(contact) }
        } finally {
            if (realm != null) {
                close()
            }
        }
    }

    fun addElement(contacts: List<Contact>) {
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance()
            realm!!.executeTransaction { realm -> realm.insertOrUpdate(contacts) }
        } finally {
            if (realm != null) {
                close()
            }
        }

    }



    /**
     * delete （删）
     */
    fun deleteElement(id: String) {
        val contact = realm!!.where(Contact::class.java).equalTo("id", id).findFirst()
        realm!!.beginTransaction()
        contact.deleteFromRealm()
        realm!!.commitTransaction()

    }

    fun deleteAllFromRealm() {
        realm!!.executeTransaction { realm -> realm.deleteAll() }
    }

    /**
     * update （改）
     */
    //    public void updateElement(String id, String[] line) {
    //        Search mSearch = mRealm.where(Search.class).equalTo("id", id).findFirst();
    //        mRealm.beginTransaction();
    ////        mSearch.saveLine(line);
    //        mRealm.copyToRealmOrUpdate(mSearch);
    //        mRealm.commitTransaction();
    //    }
    //
    //    public void updateElement(Search survey) {
    //        Search mSearch = mRealm.where(Search.class).equalTo("id", survey.getCode()).findFirst();
    //        mRealm.beginTransaction();
    ////        mPerson.setLineOne(mSearch.getLineOne());
    //        mRealm.copyToRealmOrUpdate(mSearch);
    //        mRealm.commitTransaction();
    //    }

    /**
     * query
     */
    fun queryAll(): List<Contact> {
        val mContacts = realm!!.where(Contact::class.java).findAll()
        return realm!!.copyFromRealm(mContacts)
    }

    /**
     * query
     */
    fun queryById(id: Int): Contact {
        var model = realm!!.where(Contact::class.java).equalTo("id", id).findFirst()
        return realm!!.copyFromRealm(model)
    }

    fun close() {
        if (realm != null) {
            realm!!.close()
        }
    }

    fun getRealmInstance():Realm{
        return realm!!
    }

    companion object {

        val DB_NAME = "Leroy.realm"
    }

}
