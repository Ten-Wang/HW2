package tw.teng.hw2.resource.utils

import tw.teng.hw2.resource.repository.model.*

class DataUtils {
    companion object {
        fun getItemList(): ArrayList<ListItem> {
            return arrayListOf(
                TitleItem("DAY PASS"),
                DayPass(),
                Day3Pass(),
                Day7Pass(),
                TitleItem("HOUR PASS"),
                HourPass(),
                Hour8Pass()
            )
        }
    }
}