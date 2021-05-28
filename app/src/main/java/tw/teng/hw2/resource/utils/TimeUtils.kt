package tw.teng.hw2.resource.utils

import java.text.SimpleDateFormat
import java.util.*

open class TimeUtils {
    companion object {
        private const val DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss"

        /**
         * 將 long 轉成 [format] 格式的時間字串
         *
         * @param format [format]
         * @param time   時間
         * @return String 格式為 [format] 格式
         */
        fun toString(format: String, time: Long): String {
            val formatter = SimpleDateFormat(format, Locale.TAIWAN)
            return formatter.format(Date(time))
        }

        fun toString(time: Long): String {
            return toString(DATE_TIME_FORMAT, time)
        }

    }
}