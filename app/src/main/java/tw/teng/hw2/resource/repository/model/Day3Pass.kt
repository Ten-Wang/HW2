package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

class Day3Pass : DayPass() {
    override val name: String = "3 Day Pass"

    init {
        duration = TimeUnit.DAYS.toMillis(3)
        rp = 5000
    }

    @Override
    override fun onClick() {
        super.onClick()
    }
}