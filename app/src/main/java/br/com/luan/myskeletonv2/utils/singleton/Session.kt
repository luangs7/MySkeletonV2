package br.com.luan.myskeletonv2.utils.singleton

import br.com.luan.myskeletonv2.view.ui.Realm.model.User

/**
 * Created by luan silva on 19/04/18.
 */


class Session{

    private var user:User? = null

    private val token by lazy{
//        user.token
        "FKDJKFDSAJK8743KFSDAMK"
    }

    var isUserinSession:Boolean = false


    companion object {
        var shared = Session()
    }

    //below this I can put the variables to cache


    init {

    }

//    private constructor()


    internal fun logout(){
        user = null
        //clear shared preferences here
    }

    //    fun getSharedAuth(context: Context): User? {
//        return LocalDbImplement<User>(context).getDefault(User::class.java) as? User
//    }
//
//    fun setShared(context: Context, `object`: User) {
//        LocalDbImplement<User>(context).save(`object`)
//    }
//
//    fun getSharedCar(context: Context): Car? {
//        return LocalDbImplement<Car>(context).getDefault(Car::class.java) as? Car
//    }
//
//    fun setShared(context: Context, `object`: Car) {
//        LocalDbImplement<Car>(context).save(`object`)
//    }
//
//    fun clearShared(context: Context) {
//        LocalDbImplement<User>(context).clearObject(User::class.java)
//        LocalDbImplement<Car>(context).clearObject(Car::class.java)
//    }

}