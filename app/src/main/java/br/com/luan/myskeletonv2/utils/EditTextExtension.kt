package br.com.luan.myskeletonv2.utils

import android.text.method.PasswordTransformationMethod
import android.view.View.OnFocusChangeListener
import android.view.animation.AnimationUtils
import android.widget.EditText
import br.com.luan.myskeletonv2.R
import br.com.luan.myskeletonv2.extras.mask.MyMaskEditText
import br.com.luan.myskeletonv2.extras.mask.SuperBrazilianTelephoneMask
import java.util.regex.Pattern
/**
 * Created by luan gabriel on 16/04/18.
 */


fun EditText.textTrim(): String {
    return this.text.toString().trim()
}

fun EditText.getTextString(): String {
    return this.text.toString()
}

fun EditText.shakeView() {
    val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
    this.startAnimation(shake)
}

fun EditText.isPasswordValid(maxLenght: Int): Boolean {
    return this.textTrim().length > maxLenght
}

fun EditText.checkEmpty(editText: EditText): Boolean {
    return editText.text.trim().length < 0
}

fun EditText.checkEdittextError(error: String) {
    this.error = error
    this.setFocusableInTouchMode(true)
    this.requestFocus()
    this.shakeView()
}

fun EditText.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this.textTrim())
            .matches()
}

fun EditText.isLicensePlate(): Boolean {
    val m = Pattern.compile("[A-Z]{3}\\d{4}").matcher(this.getTextString().replace("-", "").toUpperCase())

    return m.find()
}

fun EditText.validCep(): Boolean {
    val pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$")
    val matcher = pattern.matcher(this.getTextString())
    return matcher.find()
}

fun EditText.checkCep(completion: () -> Unit){
    this.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            if(this.validCep()){
                completion()
            }
        }
    }
}


fun EditText.passwordToggledVisible() {
    val selection = selectionStart
    transformationMethod = if (transformationMethod == null) PasswordTransformationMethod() else null
    setSelection(selection)
}

fun EditText.addCPFMask() {
//    this.addTextChangedListener(CpfCnpjMask.insert(this,this))
}

fun EditText.addCEPMask() {
    this.addTextChangedListener(MyMaskEditText(this, "#####-###"))
}

fun EditText.addPhoneMask() {
    this.addTextChangedListener(SuperBrazilianTelephoneMask(this))
}
