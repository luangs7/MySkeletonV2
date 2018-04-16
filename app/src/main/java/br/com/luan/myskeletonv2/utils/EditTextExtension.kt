package br.com.luan.myskeletonv2.utils

import android.view.animation.AnimationUtils
import android.widget.EditText
import br.com.luan.myskeletonv2.R

/**
 * Created by luan gabriel on 16/04/18.
 */


fun EditText.textTrim():String{
    return this.text.toString().trim()
}

fun EditText.getText():String{
    return this.text.toString()
}

fun EditText.shakeView(){
    val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
    this.startAnimation(shake)
}

fun EditText.isPasswordValid(maxLenght:Int):Boolean{
    return this.textTrim().length > maxLenght
}

fun EditText.checkEmpty(editText: EditText): Boolean {
    return editText.text.trim().length < 0
}

fun EditText.checkEdittextError(error:String){
    this.error = error
    this.setFocusableInTouchMode(true)
    this.requestFocus()
    this.shakeView()
}

fun EditText.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this.textTrim())
            .matches()
}



//fun EditText.checkEmptyMultiple(editTexts: Array<EditText>): Boolean {
//
//    for (currentField in editTexts) {
//        if (currentField.text.toString().length <= 0) {
//            return true
//        }
//    }
//    return false
//}
//
//fun checkEmptyMultipleWithError(editTexts: ArrayList<EditText>, context: Context): Boolean {
//    var hasEmpty:Boolean =false
//    val wrongs: ArrayList<EditText> = ArrayList()
//    for (currentField in editTexts) {
//        if (currentField.text.toString().length <= 0) {
//            currentField.error = "Esse campo não pode ser vazio!"
//            shakeEditText(currentField,context)
//            hasEmpty = true
//            wrongs.add(currentField)
//        }
//    }
//    if(wrongs.size > 0) {
//        wrongs.get(0).setFocusableInTouchMode(true)
//        wrongs.get(0).requestFocus()
//    }
//    return hasEmpty
//}

