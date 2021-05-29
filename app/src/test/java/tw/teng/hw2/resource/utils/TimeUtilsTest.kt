package tw.teng.hw2.resource.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeUtilsTest {

    @Test
    fun `test toString`() {
        val expected = "2021/05/29 21:41:15"
        val actual = 1622295675000
        assertEquals(expected, TimeUtils.toString(actual))
    }
}