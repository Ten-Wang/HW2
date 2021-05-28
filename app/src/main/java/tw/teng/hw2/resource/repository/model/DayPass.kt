package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

open class DayPass : HourPass() {
    override val name: String = "1 Day Pass"

    init {
        duration = TimeUnit.HOURS.toMillis(24)
        rp = 2000
    }

    @Override
    override fun onClick() {
        super.onClick()
    }
}