package br.com.luan.myskeletonv2.extras

import android.content.Context
import android.view.View
import android.widget.EditText
import br.com.luan.myskeletonv2.utils.shakeView


/**
 * Created by Luan on 04/05/17.
 */

class Utils {

    var contentView: View? = null

    constructor(contentView: View) {
        this.contentView = contentView
    }

    constructor() {}

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


    companion object {

        fun checkEmpty(editText: EditText): Boolean {
            return if (editText.text.trim().length < 0)
                true
            else
                false
        }

        fun checkEmptyMultiple(editTexts: Array<EditText>): Boolean {

            for (currentField in editTexts) {
                if (currentField.text.toString().length <= 0) {
                    return true
                }
            }
            return false
        }

        fun checkEmptyMultipleWithError(editTexts: ArrayList<EditText>, context: Context): Boolean {
            var hasEmpty:Boolean =false
            val wrongs: ArrayList<EditText> = ArrayList()
            for (currentField in editTexts) {
                if (currentField.text.toString().length <= 0) {
                    currentField.error = "Esse campo não pode ser vazio!"
                    currentField.shakeView()
                    hasEmpty = true
                    wrongs.add(currentField)
                }
            }
            if(wrongs.size > 0) {
                wrongs.get(0).setFocusableInTouchMode(true)
                wrongs.get(0).requestFocus()
            }
            return hasEmpty
        }




    }
}
