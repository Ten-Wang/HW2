package tw.teng.hw2.resource.repository.model

import java.util.concurrent.TimeUnit

open class HourPass : ListItem() {
    open val name: String = "1 Hour Pass"
    var duration = TimeUnit.HOURS.toMillis(1)
    var rp = 500

    override fun onClick() {
        TODO("Not yet implemented")
    }
}