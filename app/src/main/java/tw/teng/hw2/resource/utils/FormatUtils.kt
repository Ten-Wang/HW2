package tw.teng.hw2.resource.utils

import java.text.DecimalFormat

class FormatUtils {
    companion object {
        fun getRpDecimalFormat(
            rp: Int
        ): String {
            val mDecimalFormat = DecimalFormat("###,###,###,###")
            return mDecimalFormat.format(rp).replace(",", ".")
        }
    }
}