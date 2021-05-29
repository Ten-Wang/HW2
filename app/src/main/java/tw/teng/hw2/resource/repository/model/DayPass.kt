package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

open class DayPass : HourPass() {
    init {
        name = "1 Day Pass"
        duration = TimeUnit.HOURS.toMillis(24)
        rp = 2000
    }
}