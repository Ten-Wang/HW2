package tw.teng.hw2.ui

import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test
import tw.teng.hw2.resource.repository.model.*
import tw.teng.hw2.ui.RecyclerViewAdapter.Companion.TYPE_PASS
import tw.teng.hw2.ui.RecyclerViewAdapter.Companion.TYPE_TITLE

class RecyclerViewAdapterTest {

    @Test
    fun `test getItemViewType`() {
        val recyclerViewAdapter = RecyclerViewAdapter(arrayListOf(), mockk())
        val arrayList = arrayListOf(
            TitleItem("DAY PASS"),
            DayPass(),
            Day3Pass(),
            Day7Pass(),
            TitleItem("HOUR PASS"),
            HourPass(),
            Hour8Pass()
        )
        recyclerViewAdapter.setItems(arrayList)

        assertTrue(recyclerViewAdapter.getItemViewType(0) == TYPE_TITLE)
        assertTrue(recyclerViewAdapter.getItemViewType(1) == TYPE_PASS)
    }

    @Test
    fun `test getItemCount`() {
        val recyclerViewAdapter = RecyclerViewAdapter(arrayListOf(), mockk())
        val arrayList = arrayListOf(
            TitleItem("DAY PASS"),
            DayPass(),
            Day3Pass(),
            Day7Pass(),
            TitleItem("HOUR PASS"),
            HourPass(),
            Hour8Pass()
        )
        recyclerViewAdapter.setItems(arrayList)

        assertTrue(arrayList.size == recyclerViewAdapter.itemCount)
    }
}