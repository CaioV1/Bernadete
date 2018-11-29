package com.digitalindividual.util

import android.util.Log
import java.text.SimpleDateFormat

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

        fun dateToMillisecond(date: String): Long{

            val simpleDate: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmm")

            var timeMill:Long = 0

            try {

                val datetime = simpleDate.parse(date)

                timeMill = datetime.time

            } catch (exception: Exception){

                Log.d("@@@@@@@", date)

            }

            return timeMill

        }

    }

}