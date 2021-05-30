package tw.teng.hw2.resource.repository.model

class NoPass : DayPass() {
    init {
        name = "No Pass"
        duration = 0
        rp = 0
    }
}