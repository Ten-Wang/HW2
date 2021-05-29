package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

class Day7Pass : DayPass() {
    init {
        name = "7 Day Pass"
        duration = TimeUnit.DAYS.toMillis(7)
        rp = 10000
    }
}