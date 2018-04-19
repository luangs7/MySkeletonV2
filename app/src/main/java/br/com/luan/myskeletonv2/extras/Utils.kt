package br.com.luan.myskeletonv2.extras

import android.content.Context
import android.widget.EditText
import br.com.luan.myskeletonv2.utils.shakeView


/**
 * Created by Luan on 04/05/17.
 */

class Utils {

    companion object {

        fun checkEmpty(editText: EditText): Boolean {
            return editText.text.trim().length < 0
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
