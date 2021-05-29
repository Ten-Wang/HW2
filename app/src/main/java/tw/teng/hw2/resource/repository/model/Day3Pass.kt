package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

class Day3Pass : DayPass() {
    init {
        name = "3 Day Pass"
        duration = TimeUnit.DAYS.toMillis(3)
        rp = 5000
    }
}