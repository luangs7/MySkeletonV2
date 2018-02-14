package br.com.luan.myskeletonv2.extras

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.data.dao.LocalDbImplement
import java.text.SimpleDateFormat
import java.util.regex.Pattern


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

        fun closeKey(mActivity: Activity) {
            val view = mActivity.currentFocus
            if (view != null) {
                val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }


        fun setListViewHeightBasedOnChildren(listView: ListView) {
            val listAdapter = listView.adapter ?: // pre-condition
                    return

            var totalHeight = 0
            for (i in 0 until listAdapter.count) {
                val listItem = listAdapter.getView(i, null, listView)
                listItem.measure(0, 0)
                totalHeight += listItem.measuredHeight
            }

            val params = listView.layoutParams
            params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
            listView.layoutParams = params
            listView.requestLayout()
        }

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
                    shakeEditText(currentField,context)
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

       fun checkEdittextError(context: Context,error:String, currentField: EditText){
           currentField.error = error
           currentField.setFocusableInTouchMode(true)
           currentField.requestFocus()
           shakeEditText(currentField,context)
       }

        val currentTimeMonthAndYear: String
            get() {
                val date = System.currentTimeMillis()
                val dateFormat = SimpleDateFormat("MMMM yyyy")

                return dateFormat.format(date)
            }

        fun getCurrentTimeFormatted(format: String): String {
            val date = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat(format)

            return dateFormat.format(date)
        }

        fun isEmailValid(email: CharSequence): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()
        }

        fun isPasswordValid(editText: EditText): Boolean{
            if(editText.text.toString().length > 5){
                return true
            }else
                return false
        }

        fun licensePlate(plate: String): Boolean {
            val m = Pattern.compile("[A-Z]{3}\\d{4}").matcher(plate.replace("-", "").toUpperCase())

            return m.find()
        }

        fun validCep(cep: String): Boolean{
            val pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$")
            val matcher = pattern.matcher(cep)
            return matcher.find()
        }

        fun getTextTrim(editText: EditText): String {
            return editText.text.toString().trim { it <= ' ' }
        }

        fun getDefaultValue(editText: EditText, defaultValue: String): String {
            return if (editText.text.length > 0)
                editText.text.toString().trim { it <= ' ' }
            else
                defaultValue

        }

        fun getText(editText: EditText): String {
            return editText.text.toString()
        }

        fun shakeEditText(field:EditText, context: Context){
            val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
            field.startAnimation(shake)
        }


    }
}
