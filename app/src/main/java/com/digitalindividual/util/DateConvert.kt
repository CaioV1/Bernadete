package com.digitalindividual.util

import android.util.Log

/**
 * Created by 17170077 on 13/11/2018.
 */
class DateConvert {

    companion object {

        fun stringToSQL(dateString: String): Long{

            val dateArray = dateString.split("/")

            val array = dateArray[2].split(" ")

            val hourArray = array[1].split(":")

            val dateSQL = "${array[0]}${dateArray[1]}${dateArray[0]}${hourArray[0]}${hourArray[1]}".toLong()

            return dateSQL

        }

        fun SQLToString(dateSQL: Long): String{

            var dateString = dateSQL.toString()

            val ano = dateString.substring(0, 4)
            val mes = dateString.substring(4, 6)
            val dia = dateString.substring(6, 8)
            val hora = dateString.substring(8, 10)
            val minuto = dateString.substring(10, 12)

            dateString = "${dia}/${mes}/${ano} ${hora}:${minuto}"

            return dateString

        }

    }

}