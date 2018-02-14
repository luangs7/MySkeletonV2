package br.com.luan.myskeletonv2.extras

/**
 * Created by bsilva on 15/04/17.
 */

import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailValidator {

    private val pattern: Pattern
    private var matcher: Matcher? = null

    init {
        pattern = Pattern.compile(EMAIL_PATTERN)
    }

    fun isValid(hex: String): Boolean {
        matcher = pattern.matcher(hex)
        return matcher!!.matches()
    }

    companion object {

        private val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }
}