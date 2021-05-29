package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

class Day7Pass : DayPass() {
    override val name: String = "7 Day Pass"

    init {
        duration = TimeUnit.DAYS.toMillis(7)
        rp = 10000
    }
}