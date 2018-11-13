package com.digitalindividual.util

import android.util.Log

/**
 * Created by 17170077 on 13/11/2018.
 */
class DateConvert {

    companion object {

        fun stringToSQL(dateString: String): Int{

            val dateSQL = dateString.replace("/", "").toInt()

            return dateSQL

        }

    }

}