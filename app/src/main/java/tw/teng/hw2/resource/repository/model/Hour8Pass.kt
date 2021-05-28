package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

class Hour8Pass : HourPass() {
    override val name: String = "8 Hour Pass"

    init {
        duration = TimeUnit.HOURS.toMillis(8)
        rp = 1000
    }

    @Override
    override fun onClick() {
        super.onClick()
    }
}