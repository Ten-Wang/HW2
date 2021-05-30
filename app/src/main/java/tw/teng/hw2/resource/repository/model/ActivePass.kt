package tw.teng.hw2.resource.repository.model

import tw.teng.hw2.resource.utils.TimeUtils

class ActivePass {
    var passStart: Long = 0
    var dayPass: DayPass = NoPass()
    var hourPass: HourPass = NoPass()

    fun getName(): String {
        if (dayPass !is NoPass && hourPass !is NoPass) {
            return dayPass.name + " and " + hourPass.name
        }
        return if (dayPass is NoPass) {
            hourPass.name
        } else {
            dayPass.name
        }
    }

    fun getExpiredTime(): String {
        val totalDuration = hourPass.duration + (dayPass.duration)
        if (passStart == 0L) {
            passStart = System.currentTimeMillis()
        }
        return TimeUtils.toString(passStart + totalDuration)
    }
}