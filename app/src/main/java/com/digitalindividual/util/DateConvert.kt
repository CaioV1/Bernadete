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

        fun SQLToString(dateSQL: Long): String{

            var dateString = dateSQL.toString()

            dateString = "${dateString.substring(0, 2)}/" +
                    "${dateString.substring(2, 4)}/" +
                    "${dateString.substring(4, 6)}" +
                    "${dateString.substring(6, 8)}"

            return dateString

        }

    }

}