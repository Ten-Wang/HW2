package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

class Hour8Pass : HourPass() {
    init {
        name = "8 Hour Pass"
        duration = TimeUnit.HOURS.toMillis(8)
        rp = 1000
    }
}