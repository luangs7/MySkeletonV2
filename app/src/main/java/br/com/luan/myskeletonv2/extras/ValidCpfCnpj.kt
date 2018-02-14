package br.com.luan.myskeletonv2.extras

/**
 * Created by Luan on 27/11/17.
 */

object ValidCpfCnpj {


    private val pesoCPF = intArrayOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)
    private val pesoCNPJ = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    fun isValid(mcnpj_cpf: String): Boolean {
        var cnpj_cpf = mcnpj_cpf

        if (cnpj_cpf == "NA") {
            return true
        }

        cnpj_cpf = cnpj_cpf.replace("\\D+".toRegex(), "")

        if (cnpj_cpf.length == 11) {
            return isValidCPF(cnpj_cpf)
        } else if (cnpj_cpf.length == 14) {
            return isValidCNPJ(cnpj_cpf)
        }
        return false
    }

    private fun isValidCPF(cpf: String?): Boolean {
        if (cpf == "00000000000" || cpf == "11111111111" ||
                cpf == "22222222222" || cpf == "33333333333" ||
                cpf == "44444444444" || cpf == "55555555555" ||
                cpf == "66666666666" || cpf == "77777777777" ||
                cpf == "88888888888" || cpf == "99999999999" ||
                cpf == null || cpf.length != 11)
            return false

        val digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF)
        val digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF)
        return cpf == cpf.substring(0, 9) + digito1.toString() + digito2.toString()
    }

    private fun isValidCNPJ(cnpj: String?): Boolean {
        if (cnpj == "00000000000000" || cnpj == "11111111111111" ||
                cnpj == "22222222222222" || cnpj == "33333333333333" ||
                cnpj == "44444444444444" || cnpj == "55555555555555" ||
                cnpj == "66666666666666" || cnpj == "77777777777777" ||
                cnpj == "88888888888888" || cnpj == "99999999999999" ||
                cnpj == null || cnpj.length != 14)
            return false

        val digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ)
        val digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ)
        return cnpj == cnpj.substring(0, 12) + digito1.toString() + digito2.toString()
    }

    private fun calcularDigito(str: String, peso: IntArray): Int {
        var soma = 0
        var indice = str.length - 1
        var digito: Int
        while (indice >= 0) {
            digito = Integer.parseInt(str.substring(indice, indice + 1))
            soma += digito * peso[peso.size - str.length + indice]
            indice--
        }
        soma = 11 - soma % 11
        return if (soma > 9) 0 else soma
    }

}
